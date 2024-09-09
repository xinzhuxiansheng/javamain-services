package com.javamain.flink.httpserver.service;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.japi.pf.FI;
import com.javamain.flink.httpserver.JobStatusMessages;
import com.javamain.flink.httpserver.dao.JobStatusDao;

public class JobStatusActor extends AbstractActor {
    private JobStatusDao jobStatusDao = new JobStatusDao();

    static Props props() {
        return Props.create(JobStatusActor.class);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(JobStatusMessages.CreateJobStatusMessage.class, handleCreateUser())
                .match(JobStatusMessages.GetJobStatusMessage.class, handleGetUser())
                .build();
    }

    private FI.UnitApply<JobStatusMessages.CreateJobStatusMessage> handleCreateUser() {
        return createJobStatusMessageMessage -> {
            jobStatusDao.pushJobStatus(createJobStatusMessageMessage.getJs());
            getSender().tell(new JobStatusMessages.ActionPerformed(String.format("User %s created.", createJobStatusMessageMessage.getJs()
                    .getApp())), getSelf());
        };
    }

    private FI.UnitApply<JobStatusMessages.GetJobStatusMessage> handleGetUser() {
        return getJobStatusMessage -> {
            getSender().tell(jobStatusDao.getJobStatus(getJobStatusMessage.getApp()), getSelf());
        };
    }
}
