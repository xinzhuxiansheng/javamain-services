package com.javamain.temp.pojo;

@lombok.Data
public class Data {
  private String _id;
  private String docId;
  private int version;
  private boolean latestVersion;
  private boolean latestDocVersion;
  private String businessId;
  private String businessIdNo;
  private int finalizeStatus;
  private int moduleType;
  private String moduleName;
  private int businessConfigType;
  private int useType;
  private String unitName;
  private String textbookId;
  private String textbookVersionId;
  private String textbookLevelId;
  private String textbookUnitId;
  private Object createTime;
  private Object updateTime;
  private long createUser;
  private long updateUser;
  private int frozenStatus;
  private int auditStatus;
  private String _class;
  private Object groups;

  private String groupId;
  private String groupName;
  private String resourceId;
  private String resourceType;
}

