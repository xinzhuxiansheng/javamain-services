package com.javamain.longpolling.server;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@SpringBootApplication
@RestController
public class ConfigServer {
    private static final Logger logger = LoggerFactory.getLogger(ConfigServer.class);

    //v1
    private final Multimap<String, AsyncContext> asyncContextResult =
            Multimaps.synchronizedSetMultimap(HashMultimap.create());

    @RequestMapping("/notifications/v1")
    public void pollNotificationV1(HttpServletRequest request, HttpServletResponse response) {
        // 根据key来获取对应的AsyncContext
        String key = request.getParameter("key");
        AsyncContext asyncContext = request.startAsync(request, response);
        asyncContext.setTimeout(20000);
        asyncContext.addListener(new AsyncListener() {
            @Override
            public void onComplete(AsyncEvent asyncEvent) throws IOException {
                logger.info("onComplete");
                asyncContextResult.remove(key, asyncContext);
            }

            @Override
            public void onTimeout(AsyncEvent asyncEvent) throws IOException {
                logger.info("onTimeout");
                response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
                asyncContext.complete();
            }

            @Override
            public void onError(AsyncEvent asyncEvent) throws IOException {
                logger.info("onError");
            }

            @Override
            public void onStartAsync(AsyncEvent asyncEvent) throws IOException {
                logger.info("onStartAsync");
            }
        }, request, response);
        asyncContextResult.put(key, asyncContext);
    }

    //v2
    private final Multimap<String, DeferredResult> deferredResults =
            Multimaps.synchronizedSetMultimap(HashMultimap.create());

    @RequestMapping("/notifications/v2")
    public DeferredResult<ResponseEntity<String>> pollNotificationV2(HttpServletRequest request, HttpServletResponse response) {
        // 根据key来获取对应的AsyncContext
        String key = request.getParameter("key");
        ResponseEntity<String>
                NOT_MODIFIED_RESPONSE = new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        DeferredResult dr = new DeferredResult<>(20 * 1000L, NOT_MODIFIED_RESPONSE);
        deferredResults.put(key, dr);
        dr.onTimeout(() -> {
            logger.info("onTimeout");
        });

        dr.onCompletion(() -> {
            logger.info("onCompletion");
            deferredResults.remove(key, dr);
        });

        return dr;
    }


    @RequestMapping("/publishConfig")
    public void publishConfig(String key, String value) {
        try {
            logger.info("publish config key:{} , value:{}", key, value);
            Collection<AsyncContext> asyncContexts = asyncContextResult.removeAll(key);
            for (AsyncContext asyncContext : asyncContexts) {
                HttpServletResponse response = (HttpServletResponse) asyncContext.getResponse();
                response.getWriter().println(value);
                asyncContext.complete();
            }

            Collection<DeferredResult> deferreds = deferredResults.removeAll(key);
            for (DeferredResult deferredResult : deferreds) {
                deferredResult.setResult(new ResponseEntity<>(value, HttpStatus.OK));
            }
        } catch (Exception e) {
            logger.error("", e);
        }
    }


    public static void main(String[] args) {
        SpringApplication.run(ConfigServer.class, args);
    }
}
