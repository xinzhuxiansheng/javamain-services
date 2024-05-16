package com.javamain.flink.httpserver.route.v0;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.StatusCodes;
import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.PathMatchers;
import akka.http.javadsl.server.Route;
import akka.pattern.PatternsCS;
import akka.util.Timeout;
import com.javamain.flink.httpserver.JobStatusMessages;
import com.javamain.flink.httpserver.model.JobStatus;
import com.javamain.flink.httpserver.service.JobStatusActor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.concurrent.ExecutionContextExecutor;
import scala.concurrent.duration.Duration;

import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

import static akka.http.javadsl.server.PathMatchers.segment;


public class JobRoutes extends AllDirectives {
    private static final Logger logger = LoggerFactory.getLogger(JobRoutes.class);
    Timeout timeout = new Timeout(Duration.create(60, TimeUnit.SECONDS));
    private final ActorSystem actorSystem;
    private final ExecutionContextExecutor executor;

    public JobRoutes(ActorSystem actorSystem, ExecutionContextExecutor executor) {
        this.actorSystem = actorSystem;
        this.executor = executor;
    }


    public Route routes() {
        return concat(
                getJobStatusRoutes()
        );
    }

    private Route getJobStatusRoutes() {
        return route(
                get(() -> route(
                        path(segment("jobStatus").slash(PathMatchers.segment()), (app) -> {
                            logger.info("/jobStatus/{} called", app);

                            //ActorRef jobStatusActor = getContext().actorOf(JobStatusActor.props(), "jobStatusActor_" + request.getJs().getApp()
                            //+ "_" + java.util.UUID.randomUUID().toString());

                            ActorRef jobStatusActor = actorSystem.actorOf(
                                    Props.create(JobStatusActor.class),
                                    "jobStatusActor" + java.util.UUID.randomUUID().toString());
                            CompletionStage<Optional<JobStatus>> future = PatternsCS.ask(jobStatusActor,
                                            new JobStatusMessages.GetJobStatusMessage(app), timeout)
                                    .thenApply(obj -> (Optional<JobStatus>) obj);
                            return onComplete(future, maybeJobStatus -> {
                                if (maybeJobStatus.isSuccess()) {
                                    Optional<JobStatus> result = maybeJobStatus.get();
                                    if (result.isPresent()) {
                                        return complete(StatusCodes.OK, result.get(), Jackson.marshaller());
                                    } else {
                                        return complete(StatusCodes.NOT_FOUND);
                                    }
                                } else {
                                    // 处理失败情况
                                    return complete(StatusCodes.INTERNAL_SERVER_ERROR, "Error retrieving job status");
                                }
                            });
                        })
                ))
        );
    }

//    private Route handleRequest(CompletionStage<Optional<JobStatus>> jobStatus) {
//        return onComplete(jobStatus, maybeJobStatus -> {
//            if (maybeJobStatus.isSuccess()) {
//                Optional<JobStatus> result = maybeJobStatus.get();
//                if (result.isPresent()) {
//                    return complete(StatusCodes.OK, result.get(), Jackson.marshaller());
//                } else {
//                    return complete(StatusCodes.NOT_FOUND);
//                }
//            } else {
//                // 处理失败情况
//                return complete(StatusCodes.INTERNAL_SERVER_ERROR, "Error retrieving job status");
//            }
//        });
//    }

//    private Route getJobStats(String app) {
//        return get(() -> {
//            CompletionStage<Optional<JobStatus>> user = PatternsCS.ask(serviceManager,
//                            new JobStatusMessages.GetJobStatusMessage(app), timeout)
//                    .thenApply(obj -> (Optional<JobStatus>) obj);
//            return onSuccess(() -> user, performed -> {
//                if (performed.isPresent())
//                    return complete(StatusCodes.OK, performed.get(), Jackson.marshaller());
//                else
//                    return complete(StatusCodes.NOT_FOUND);
//            });
//        });
//    }


//    private Route postUser() {
//        return route(post(() -> entity(Jackson.unmarshaller(JobStatus.class), user -> {
//            CompletionStage<JobStatusMessages.ActionPerformed> userCreated = PatternsCS.ask(serviceManager,
//                            new JobStatusMessages.CreateJobStatusMessage(user), timeout)
//                    .thenApply(obj -> (JobStatusMessages.ActionPerformed) obj);
//
//            return onSuccess(() -> userCreated, performed -> {
//                return complete(StatusCodes.CREATED, performed, Jackson.marshaller());
//            });
//        })));
//    }


//    public Route createRoute() {
//        return concat(
//                pathPrefix("job", () -> concat(
//                        path("status", () ->
//                                get(() ->
//                                        complete("Job status API is running")
//                                )
//                        )
//                )),
//                path("pushJobStatus", () ->
//                        post(() ->
//                                entity(Jackson.unmarshaller(JobStatusRequest.class), jobStatusRequest -> {
//                                    // 处理接收到的 jobStatusRequest
//                                    Future<Object> future = Patterns.ask(serviceManager, jobStatusRequest,
//                                            Timeout.create(Duration.ofSeconds(30)));
//
//
//                                    // 使用 onComplete 处理异步操作结果
//                                    return onComplete(future, result -> {
//                                        if (result.isSuccess()) {
//                                            return complete("Async operation result: " + result.get());
//                                        } else {
//                                            Throwable failure = result.failed().get();
//                                            return complete("Async operation failed: " + failure.getMessage());
//                                        }
//                                    });
//
//                                })
//                        )
//                )
//        );
//    }


//    private Route pushJobStatus() {
//        return route(post(() -> entity(Jackson.unmarshaller(JobStatusRequest.class), jobStatusRequest -> {
//            CompletionStage<ApiResponse> userCreated =
//                    PatternsCS.ask(serviceManager, jobStatusRequest,
//                    Timeout.create(Duration.ofSeconds(30)))
//                            .thenApply(obj -> (ApiResponse) obj);
//
//            return onSuccess(() -> userCreated, performed -> {
//                return complete(StatusCodes.CREATED, performed, Jackson.marshaller());
//            });
//        })));
//    }

}
