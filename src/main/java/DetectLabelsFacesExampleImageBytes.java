import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.*;
import com.amazonaws.util.IOUtils;

import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.FaceMatch;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.S3Object;
import com.amazonaws.services.rekognition.model.SearchFacesByImageRequest;
import com.amazonaws.services.rekognition.model.SearchFacesByImageResult;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import java.nio.ByteBuffer;
import java.util.List;


public class DetectLabelsFacesExampleImageBytes {
    public static void main(String[] args) throws Exception {


        deteckExample();


    }


    public static void deteckExample(){
        String photo="src\\main\\resources\\test3.jpg";





        ByteBuffer imageBytes = null;
        try (InputStream inputStream = new FileInputStream(new File(photo))) {
            imageBytes = ByteBuffer.wrap(IOUtils.toByteArray(inputStream));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.defaultClient();

        DetectFacesRequest request = new DetectFacesRequest()
                .withImage(new Image()
                        .withBytes(imageBytes))
                .withAttributes("ALL");
        try {

            DetectFacesResult result = rekognitionClient.detectFaces(request);
            List<FaceDetail> faceDetails = result.getFaceDetails();

            System.out.println(faceDetails.size());

            for (FaceDetail facedetail: faceDetails) {
                System.out.println("Face Detail: " + facedetail.toString());
            }

        } catch (AmazonRekognitionException e) {
            e.printStackTrace();
        }

    }

    private static void displayFaceDetail(ByteBuffer imageBytes, AmazonRekognition rekognitionClient) {
        DetectFacesRequest dfRequest = new DetectFacesRequest()
                .withImage(new Image()
                        .withBytes(imageBytes))
                .withAttributes("ALL");

        try {

            DetectFacesResult result = rekognitionClient.detectFaces(dfRequest);
            List<FaceDetail> faceDetails = result.getFaceDetails();

            for (FaceDetail facedetail: faceDetails) {
                System.out.println("Face Detail: " + facedetail.toString());
            }

        } catch (AmazonRekognitionException e) {
            e.printStackTrace();
        }
    }

    private static void displayLabel(ByteBuffer imageBytes, AmazonRekognition rekognitionClient, String photo) {
        DetectLabelsRequest request = new DetectLabelsRequest()
                .withImage(new Image()
                        .withBytes(imageBytes))
                .withMaxLabels(10)
                .withMinConfidence(77F);

        try {

            DetectLabelsResult result = rekognitionClient.detectLabels(request);
            List<Label> labels = result.getLabels();

            System.out.println("Detected labels for " + photo);
            for (Label label : labels) {
                System.out.println(label.getName() + ": " + label.getConfidence().toString());
            }

        } catch (AmazonRekognitionException e) {
            e.printStackTrace();
        }
    }
}
