//Copyright 2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.
//PDX-License-Identifier: MIT-0 (For details, see https://github.com/awsdocs/amazon-rekognition-developer-guide/blob/master/LICENSE-SAMPLECODE.)


import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.FaceRecord;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.IndexFacesRequest;
import com.amazonaws.services.rekognition.model.IndexFacesResult;
import com.amazonaws.services.rekognition.model.QualityFilter;
import com.amazonaws.services.rekognition.model.S3Object;
import com.amazonaws.services.rekognition.model.UnindexedFace;
import java.util.List;

public class AddFacesToCollection {
    public static final String collectionId = "MyCollection";
    public static final String bucket = "contextoapresentacao";
    public static final String photo3 = "detect-analyze-faces-rekognition-sample3.jpg";
    public static final String photo1 = "detect-analyze-faces-rekognition-sample1.jpg";
    public static final String photo2 = "detect-analyze-faces-rekognition-sample2.jpg";

    public static void main(String[] args) throws Exception {

        AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.defaultClient();

        Image image3 = new Image()
                .withS3Object(new S3Object()
                        .withBucket(bucket)
                        .withName(photo3));

        Image image1 = new Image()
                .withS3Object(new S3Object()
                        .withBucket(bucket)
                        .withName(photo1));

        Image image2 = new Image()
                .withS3Object(new S3Object()
                        .withBucket(bucket)
                        .withName(photo2));

        IndexFacesRequest indexFacesRequest3 = new IndexFacesRequest()
                .withImage(image3)
                .withQualityFilter(QualityFilter.AUTO)
                .withMaxFaces(4)
                .withCollectionId(collectionId)
                .withExternalImageId(photo3)
                .withDetectionAttributes("DEFAULT");

        IndexFacesRequest indexFacesRequest1 = new IndexFacesRequest()
                .withImage(image2)
                .withQualityFilter(QualityFilter.AUTO)
                .withMaxFaces(1)
                .withCollectionId(collectionId)
                .withExternalImageId(photo1)
                .withDetectionAttributes("DEFAULT");

        IndexFacesRequest indexFacesRequest2 = new IndexFacesRequest()
                .withImage(image1)
                .withQualityFilter(QualityFilter.AUTO)
                .withMaxFaces(6)
                .withCollectionId(collectionId)
                .withExternalImageId(photo2)
                .withDetectionAttributes("DEFAULT");


        IndexFacesResult indexFacesResult1 = rekognitionClient.indexFaces(indexFacesRequest1);
        IndexFacesResult indexFacesResult2 = rekognitionClient.indexFaces(indexFacesRequest2);
        IndexFacesResult indexFacesResult3 = rekognitionClient.indexFaces(indexFacesRequest3);

        System.out.println("Results for " + photo1);
        System.out.println("Faces indexed:");
        List<FaceRecord> faceRecords1 = indexFacesResult1.getFaceRecords();

        System.out.println("Results for " + photo2);
        System.out.println("Faces indexed:");
        List<FaceRecord> faceRecords2 = indexFacesResult2.getFaceRecords();

        System.out.println("Results for " + photo3);
        System.out.println("Faces indexed:");
        List<FaceRecord> faceRecords3 = indexFacesResult3.getFaceRecords();


        System.out.println(indexFacesResult1.toString());

        for (FaceRecord faceRecord : faceRecords1) {
            System.out.println("  Face ID: " + faceRecord.getFace().getFaceId());
            System.out.println("  Location:" + faceRecord.getFaceDetail().getBoundingBox().toString());
        }

        for (FaceRecord faceRecord : faceRecords2) {
            System.out.println("  Face ID: " + faceRecord.getFace().getFaceId());
            System.out.println("  Location:" + faceRecord.getFaceDetail().getBoundingBox().toString());
        }

        for (FaceRecord faceRecord : faceRecords3) {
            System.out.println("  Face ID: " + faceRecord.getFace().getFaceId());
            System.out.println("  Location:" + faceRecord.getFaceDetail().getBoundingBox().toString());
        }

        List<UnindexedFace> unindexedFaces = indexFacesResult1.getUnindexedFaces();
        System.out.println("Faces not indexed:");
        for (UnindexedFace unindexedFace : unindexedFaces) {
            System.out.println("  Location:" + unindexedFace.getFaceDetail().getBoundingBox().toString());
            System.out.println("  Reasons:");
            for (String reason : unindexedFace.getReasons()) {
                System.out.println("   " + reason);
            }
        }
    }
}