package com.javamain.s3;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.HttpMethod;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.policy.Policy;
import com.amazonaws.auth.policy.Principal;
import com.amazonaws.auth.policy.Resource;
import com.amazonaws.auth.policy.Statement;
import com.amazonaws.auth.policy.actions.S3Actions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.S3ClientOptions;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class S3Test {
    private static Logger logger = LoggerFactory.getLogger(S3Test.class);

    private String endPoint = "xxx.xxx.xxx.xxx:32121";
    private String accessKey = "minio";
    private String secretKey = "minio123";
    private String bucketName = "yzhou";

    public AmazonS3 client = null;

    @Before
    public void initClient() {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        clientConfiguration.setProtocol(Protocol.HTTP);

        S3ClientOptions options = new S3ClientOptions();
        options.setPathStyleAccess(true);

        client = new AmazonS3Client(credentials, clientConfiguration);
        client.setEndpoint(endPoint);
        client.setS3ClientOptions(options);
    }

    @After
    public void closeClient() {
        if (client != null) {
            client.shutdown();
        }
    }

    /**
     * 列举 Buckets
     */
    @Test
    public void listBuckets() {
        List<Bucket> buckets = client.listBuckets();
        for (Bucket bucket : buckets) {
            System.out.println(bucket.getName() + "\t" +
                    StringUtils.fromDate(bucket.getCreationDate()));
        }
    }

    /**
     * 创建 Bucket
     */
    @Test
    public void createBucket() {
        String bucketName = "yzhou-tmp01";
        client.createBucket(bucketName);
        listBuckets();
    }

    /***
     * 上传文件到 Bucket，且 Bucket 内部目录自动创建
     */
    @Test
    public void uploadFile2Bucket() {
        File file = new File("/Users/a/Software/maven-repository/ant/ant/1.6.5/ant-1.6.5.jar");
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName
                , "local/app/fm-t34ep0sjvd/ant-1.6.5.jar", file);
        //上传
        PutObjectResult putObjectResult = client.putObject(putObjectRequest);
        System.out.println(putObjectResult.getETag());
    }

    /**
     * 设置权限
     */
    @Test
    public void setBucketPolicy() {
//        String policyJson = "{\n" +
//                "    \"Version\": \"2012-10-17\",\n" +
//                "    \"Statement\": [\n" +
//                "        {\n" +
//                "            \"Sid\": \"AddPerm\",\n" +
//                "            \"Effect\": \"Allow\",\n" +
//                "            \"Principal\": \"*\",\n" +
//                "            \"Action\": [\n" +
//                "                \"s3:GetObject\",\n" +
//                "                \"s3:GetObjectVersion\"\n" +
//                "            ],\n" +
//                "            \"Resource\": [\n" +
//                "                \"arn:aws:s3:::fmmanager/*\"\n" +
//                "            ]\n" +
//                "        }\n" +
//                "    ]\n" +
//                "}";

        String policyJson = "{\n" +
                "    \"Version\": \"2012-10-17\",\n" +
                "    \"Statement\": [\n" +
                "        {\n" +
                "            \"Sid\": \"AddPerm\",\n" +
                "            \"Effect\": \"Allow\",\n" +
                "            \"Principal\": \"*\",\n" +
                "            \"Action\": [\n" +
                "                \"s3:GetObject\",\n" +
                "                \"s3:GetObjectVersion\"\n" +
                "            ],\n" +
                "            \"Resource\": [\n" +
                "                \"arn:aws:s3:::yzhou/*\"\n" +
                "            ]\n" +
                "        }\n" +
                "    ]\n" +
                "}\n";
        Policy bucket_policy = Policy.fromJson(policyJson);
        SetBucketPolicyRequest request = new SetBucketPolicyRequest(bucketName, bucket_policy.toJson());
        client.setBucketPolicy(request);
    }


    @Test
    public void setBucketPolicy2() {
        Policy bucket_policy = null;
        String aaa = "{\n" +
                "    \"Version\": \"2012-10-17\",\n" +
                "    \"Statement\": [\n" +
                "        {\n" +
                "            \"Sid\": \"AddPerm\",\n" +
                "            \"Effect\": \"Allow\",\n" +
                "            \"Principal\": \"*\",\n" +
                "            \"Action\": [\n" +
                "                \"s3:GetObject\",\n" +
                "                \"s3:GetObjectVersion\"\n" +
                "            ],\n" +
                "            \"Resource\": [\n" +
                "                \"arn:aws:s3:::fmmanager/*\"\n" +
                "            ]\n" +
                "        }\n" +
                "    ]\n" +
                "}";


        bucket_policy = Policy.fromJson(aaa);

        String bucket_name = "fmmanager";

        //SetBucketPolicyRequest request = new SetBucketPolicyRequest(bucket_name, bucket_policy.toJson());
        SetBucketPolicyRequest request = new SetBucketPolicyRequest(bucket_name, getPublicReadPolicy("fmmanager"));
        client.setBucketPolicy(request);
    }


    @Test
    public void createDirectory() {
        String directory = "fmmanager-test/";

        client.putObject(bucketName, directory, new ByteArrayInputStream(new byte[0]),
                null);
    }

    @Test
    public void uploadFile() {
        File file = new File("/Users/yiche/Tmp/flink-demo-1.0.1-SNAPSHOT-1643037011651.jar");
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName
                , "local/app/fm-t34ep0sjvd/flink-demo-1.0.1-SNAPSHOT-1644547740111.jar", file);
        //设置权限属性等-非必需
//        putObjectRequest.setCannedAcl(null);
        //putObjectRequest.
        //上传
        PutObjectResult putObjectResult = client.putObject(putObjectRequest);
        System.out.println(putObjectResult.getETag());
    }


    @Test
    public void deleteObject() {
        client.deleteObject("fmflinkha", "fm-dq1rom58e4/");
    }

    @Test
    public void deleteObjects() {
        ObjectListing objects = client.listObjects(endPoint);
        ArrayList<DeleteObjectsRequest.KeyVersion> keys = new ArrayList<>();
        for (S3ObjectSummary s3ObjectSummary : objects.getObjectSummaries()) {
            if(s3ObjectSummary.getKey().startsWith("fm-hwm8u1o2x8")){
                keys.add(new DeleteObjectsRequest.KeyVersion(s3ObjectSummary.getKey()));
            }
        }

//        DeleteObjectsRequest request = new DeleteObjectsRequest()
//                .withKeys(keys)
//                .withQuiet(false);
//        client.deleteObjects(request);
    }

    @Test
    public void listCheckPointObject() {
        ObjectListing objects = client.listObjects("fmflink-checkpoint");
        for (S3ObjectSummary s3ObjectSummary : objects.getObjectSummaries()) {
            System.out.println(s3ObjectSummary.getKey());
        }
    }

    @Test
    public void listCheckPointObject02() {
        ObjectListing objects = client.listObjects("fmmanager");
        for (S3ObjectSummary s3ObjectSummary : objects.getObjectSummaries()) {
            System.out.println(s3ObjectSummary.getKey());
        }
    }

    @Test
    public void listSavePointObject() {
        ObjectListing objects = client.listObjects("fmflink-savepoint");
        for (S3ObjectSummary s3ObjectSummary : objects.getObjectSummaries()) {
            System.out.println(s3ObjectSummary.getKey());
        }
    }

    @Test
    public void download() {
        String filePath = "/Users/yiche/Tmp/test/flink-demo-1.0.1-SNAPSHOT-1644547740111.jar";
        GetObjectRequest request = new GetObjectRequest(bucketName, "local/app/fm-t34ep0sjvd/flink-demo-1.0.1-SNAPSHOT-1644547740111.jar");
        //将对象存在文件中，并返回对象的元数据
        ObjectMetadata meta = client.getObject(request, new File(filePath));
    }

//    @Test
//    public void getUrl(){
//        URL url = client.getUrl(bucketName,"/local/app/fm-t34ep0sjvd/flink-demo-1.0.1-SNAPSHOT-1644547740111.jar");
//        System.out.println(url.getPath());
//        client.
//    }

    @Test
    public void testGeneratePresignedUrlRequest() {
        System.out.println("生成预签名URL");
        String key = "local/app/fm-t34ep0sjvd/flink-demo-1.0.1-SNAPSHOT-1644547740111.jar";

        // 设置过期时间为1小时
        java.util.Date expiration = new java.util.Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 60 * 5;
        expiration.setTime(expTimeMillis);

        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucketName, key)
                        .withMethod(HttpMethod.GET)
                        .withExpiration(expiration);
        URL url = client.generatePresignedUrl(generatePresignedUrlRequest);
        System.out.println(url.toString());
    }


    @Test
    public void testGetPolicyText() {
        try {
            String bucket_name = "fmmanager";
            BucketPolicy bucket_policy = client.getBucketPolicy(bucket_name);
            String policy_text = bucket_policy.getPolicyText();
            System.out.println(policy_text);
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }
    }

    @Test
    public void testGetGrantsAsList() {
        String bucket_name = "fmmanager";
        //final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.DEFAULT_REGION).build();
        try {
            AccessControlList acl = client.getBucketAcl(bucket_name);
            List<Grant> grants = acl.getGrantsAsList();
            for (Grant grant : grants) {
                System.out.format("  %s: %s\n", grant.getGrantee().getIdentifier(),
                        grant.getPermission().toString());
            }
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }
    }

    public static String getPublicReadPolicy(String bucket_name) {
        Policy bucket_policy = new Policy().withStatements(
                new Statement(Statement.Effect.Allow)
                        .withPrincipals(Principal.AllUsers)
                        .withActions(S3Actions.GetObject)
                        .withResources(new Resource(
                                "arn:aws:s3:::" + bucket_name + "/*")));
        return bucket_policy.toJson();
    }

}
