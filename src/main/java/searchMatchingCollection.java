

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.FaceMatch;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.S3Object;
import com.amazonaws.services.rekognition.model.SearchFacesByImageRequest;
import com.amazonaws.services.rekognition.model.SearchFacesByImageResult;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;


    public class searchMatchingCollection {
        public static final String collectionId = "MyCollection";
        public static final String bucket = "contextoapresentacao";
        public static final String photo1 = "detect-analyze-faces-rekognition-sample1.jpg";
        public static final String photo2 = "detect-analyze-faces-rekognition-sample2.jpg";
        public static final String photo3 = "detect-analyze-faces-rekognition-sample3.jpg";


        public static void main(String[] args) throws Exception {

            AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.defaultClient();

            ObjectMapper objectMapper = new ObjectMapper();

            // Get an image object from S3 bucket.
            Image image=new Image()
                    .withS3Object(new S3Object()
                            .withBucket(bucket)
                            .withName(photo3));

            // Search collection for faces similar to the largest face in the image.
            SearchFacesByImageRequest searchFacesByImageRequest = new SearchFacesByImageRequest()
                    .withCollectionId(collectionId)
                    .withImage(image)
                    .withFaceMatchThreshold(70F)
                    .withMaxFaces(6);

            SearchFacesByImageResult searchFacesByImageResult =
                    rekognitionClient.searchFacesByImage(searchFacesByImageRequest);

            System.out.println("Faces matching largest face in image from " + photo3);
            List < FaceMatch > faceImageMatches = searchFacesByImageResult.getFaceMatches();


            //float faces = searchFacesByImageResult.getSearchedFaceConfidence();
            //System.out.println(searchFacesByImageResult.toString());
            //System.out.println(faces);

            for (FaceMatch face: faceImageMatches) {
                System.out.println(objectMapper.writerWithDefaultPrettyPrinter()
                        .writeValueAsString(face));
                System.out.println();
            }
        }
    }

