package com.javamain.temp;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.javamain.temp.pojo.Data;
import com.javamain.temp.pojo.Module;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;

public class CopyTest {
  public static void main(String[] args) {
    String vString = readData();

//    List<Module> modules = new ArrayList<>();
//    Long start = System.currentTimeMillis();
//    for(int i = 0; i < 100000; i++) {
//      parseGroupsField02(vString, modules);
//    }
//    Long end = System.currentTimeMillis();
//    System.out.println("parseGroupsField02 : " + (end - start) + " ms");  // 7342 ms

    List<JSONObject> modules = new ArrayList<>();
    Long start = System.currentTimeMillis();
    for(int i = 0; i < 100000; i++) {
      parseGroupsField(vString, modules);
    }
    Long end = System.currentTimeMillis();
    System.out.println("parseGroupsField : " + (end - start) + " ms");  // 612 ms

  }

  // 读取 resources/data.log 文件内容 然后作为方法返回值
  public static String readData() {
    InputStream inputStream = CopyTest.class.getClassLoader().getResourceAsStream("data.log");
    try {
      byte[] bytes = new byte[inputStream.available()];
      inputStream.read(bytes);
      return new String(bytes);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  private static void parseGroupsField02(String value, List<Module> result) {
    JSONObject vObj = JSON.parseObject(value);
    JSONObject dataObject = vObj.getJSONObject("data");
    if (dataObject == null) {
      return;
    }
    JSONArray groups = dataObject.getJSONArray("groups");
    if (groups == null || groups.isEmpty()) {
      result.add(jsonObjectToModule(vObj, dataObject, null, null));
    } else {
      for (Object group : groups) {
        JSONObject gobj = (JSONObject) group;
        JSONArray resources = gobj.getJSONArray("resources");
        if (resources == null || resources.isEmpty()) {
          result.add(jsonObjectToModule(vObj, dataObject, gobj, null));
        } else {
          for (Object resource : resources) {
            JSONObject resourceobj = (JSONObject) resource;
            result.add(jsonObjectToModule(vObj, dataObject, gobj, resourceobj));
          }
        }
      }
    }
  }


  private static Module jsonObjectToModule(JSONObject rootObj, JSONObject dataObj, JSONObject groupObj, JSONObject resourceObj) {
    Data data = new Data();
    data.set_id(dataObj.getString("_id"));
    data.setDocId(dataObj.getString("docId"));
    data.setVersion(dataObj.getInteger("version"));
    data.setLatestVersion(dataObj.getBoolean("latestVersion"));
    data.setLatestDocVersion(dataObj.getBoolean("latestDocVersion"));
    data.setBusinessId(dataObj.getString("businessId"));
    data.setBusinessIdNo(dataObj.getString("businessIdNo"));
    data.setFinalizeStatus(dataObj.getInteger("finalizeStatus"));
    data.setModuleType(dataObj.getInteger("moduleType"));
    data.setModuleName(dataObj.getString("moduleName"));
    data.setGroups(dataObj.getJSONObject("groups"));
    data.setBusinessConfigType(dataObj.getInteger("businessConfigType"));
    data.setUseType(dataObj.getInteger("useType"));
    data.setUnitName(dataObj.getString("unitName"));
    data.setTextbookId(dataObj.getString("textbookId"));
    data.setTextbookVersionId(dataObj.getString("textbookVersionId"));
    data.setTextbookLevelId(dataObj.getString("textbookLevelId"));
    data.setTextbookUnitId(dataObj.getString("textbookUnitId"));
    data.setCreateTime(dataObj.getJSONObject("createTime"));
    data.setUpdateTime(dataObj.getJSONObject("updateTime"));
    data.setCreateUser(dataObj.getLong("createUser"));
    data.setUpdateUser(dataObj.getLong("updateUser"));
    data.setFrozenStatus(dataObj.getInteger("frozenStatus"));
    data.setAuditStatus(dataObj.getInteger("auditStatus"));
    data.set_class(dataObj.getString("_class"));

    if (groupObj != null) {
      data.setGroupId(groupObj.getString("groupId"));
      data.setGroupName(groupObj.getString("groupName"));
    }

    if (resourceObj != null) {
      data.setResourceId(resourceObj.getString("resourceId"));
      data.setResourceType(resourceObj.getString("resourceType"));
    }

    Module module = new Module();
    module.setData(data);
    module.setDatabaseName(rootObj.getString("databaseName"));
    module.setTableName(rootObj.getString("tableName"));

    return module;
  }

  private static void parseGroupsField(String value, List<JSONObject> result) {
    JSONObject vObj = JSON.parseObject(value);
    JSONObject dataObject = vObj.getJSONObject("data");
    if (dataObject == null) {
      return;
    }
    String databaseName = vObj.getString("databaseName");
    String tableName = vObj.getString("tableName");
    JSONArray groups = dataObject.getJSONArray("groups");
    if (groups == null || groups.isEmpty()) {
      result.add(vObj);
    } else {
      for (Object group : groups) {
        JSONObject gobj = (JSONObject) group;
        JSONArray resources = gobj.getJSONArray("resources");
        if (resources == null || resources.isEmpty()) {
          JSONObject copyObj = JSON.copy(dataObject);
          copyObj.put("groupId", gobj.getString("groupId"));
          copyObj.put("groupName", gobj.getString("groupName"));
          copyObj.put("uniqueId", System.currentTimeMillis());
          result.add(CombinationRootPath(copyObj, databaseName, tableName));
        } else {
          for (Object resource : resources) {
            JSONObject resourceobj = (JSONObject) resource;

            JSONObject copyObj = JSON.copy(dataObject);
            copyObj.put("groupId", gobj.getString("groupId"));
            copyObj.put("groupName", gobj.getString("groupName"));
            copyObj.put("uniqueId", System.currentTimeMillis());
            copyObj.put("resourceId", resourceobj.getString("resourceId"));
            copyObj.put("resourceType", resourceobj.getInteger("resourceType"));
            result.add(CombinationRootPath(copyObj, databaseName, tableName));
          }
        }
      }
    }
  }

  private static JSONObject CombinationRootPath(JSONObject dataObj, String databaseName, String tableName) {
    JSONObject rootObj = new JSONObject();
    rootObj.put("databaseName", databaseName);
    rootObj.put("tableName", tableName);
    rootObj.put("data", dataObj);
    return rootObj;
  }
}
