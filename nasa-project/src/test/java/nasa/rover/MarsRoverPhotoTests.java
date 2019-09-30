package nasa.rover;

import static org.unitils.reflectionassert.ReflectionAssert.*;


import nasa.pojos.MarsRoverPhotoResponse;
import nasa.pojos.Photo;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;



public class MarsRoverPhotoTests {
    public final static int NUM_PHOTOS = 10;

    @Test
    public void verifyPhotosBySolEqualPhotosByEarthDate() throws MalformedURLException {
        MarsRoverPhotoService marsRoverPhotoService = new MarsRoverPhotoService(System.getenv("API_KEY"));
        marsRoverPhotoService.setRover("curiosity");
        marsRoverPhotoService.setSol(1000);
        MarsRoverPhotoResponse responseBySol = marsRoverPhotoService.getPhotosBySol();
        List<Photo> photosBySolMetadata = responseBySol.getPhotos().stream().limit(NUM_PHOTOS).collect(Collectors.toList());

        marsRoverPhotoService.setEarthDate("2015-05-30");
        MarsRoverPhotoResponse responseByEarthDate = marsRoverPhotoService.getPhotosByEarthDate();
        List<Photo> photosByEarthDateMetadata = responseByEarthDate.getPhotos().stream().limit(NUM_PHOTOS).collect(Collectors.toList());

        assertReflectionEquals(photosByEarthDateMetadata,photosBySolMetadata);

        List<BufferedImage> photosBySol = photosBySolMetadata.stream()
                .map(meta -> getImageFromUrl(meta.getImgSrc()))
                .collect(Collectors.toList());

        List<BufferedImage> photosByEarthDate = photosByEarthDateMetadata.stream()
                .map(meta -> getImageFromUrl(meta.getImgSrc()))
                .collect(Collectors.toList());

        for(int i=0;i<NUM_PHOTOS;i++){
            assertReflectionEquals(photosBySol.get(i).getData(), photosByEarthDate.get(i).getData());
        }

    }

    @Test
    public void verifyNoCameraTookMoreThan10TimesMorePhotosThanOthers(){
        MarsRoverPhotoService marsRoverPhotoService = new MarsRoverPhotoService(System.getenv("API_KEY"));
        marsRoverPhotoService.setRover("curiosity");
        marsRoverPhotoService.setSol(1000);

        Map<String, Integer> cameraNumPhotos = new HashMap<>();

        Arrays.stream(MarsRoverPhotoService.Cameras.values()).forEach(camera ->{
            if(camera!= MarsRoverPhotoService.Cameras.ALL) {
                marsRoverPhotoService.setCamera(camera);

                MarsRoverPhotoResponse response = marsRoverPhotoService.getPhotosBySol();
                cameraNumPhotos.put(camera.name(), response.getPhotos().size());
            }
        });

        int min = Collections.min(cameraNumPhotos.values());
        int max = Collections.max(cameraNumPhotos.values());

        Assert.assertNotEquals(min, 0, "A camera took 0 pictures!");
        Assert.assertTrue(max/min < 10, "A camera took more than 10 times more images than other camera.");
    }

    private BufferedImage getImageFromUrl(String imageUrl) {
        BufferedImage image = null;
        try {
            URL url = new URL(imageUrl);
            image = ImageIO.read(url);
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            return image;
        }
    }
}

