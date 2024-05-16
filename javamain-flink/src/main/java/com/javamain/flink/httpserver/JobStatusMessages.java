package com.javamain.flink.httpserver;

import com.javamain.flink.httpserver.model.JobStatus;
import lombok.Getter;

import java.io.Serializable;

public interface JobStatusMessages {
    class ActionPerformed implements Serializable {

        private static final long serialVersionUID = 1L;

        private final String description;

        public ActionPerformed(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }


    @Getter
    class CreateJobStatusMessage implements Serializable {

        private static final long serialVersionUID = 1L;
        private final JobStatus js;

        public CreateJobStatusMessage(JobStatus js) {
            this.js = js;
        }
    }

    @Getter
    class GetJobStatusMessage implements Serializable {
        private static final long serialVersionUID = 1L;
        private final String app;

        public GetJobStatusMessage(String app) {
            this.app = app;
        }
    }
}
