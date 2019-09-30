package nasa.rover;

import static org.unitils.reflectionassert.ReflectionAssert.*;


import nasa.pojos.MarsRoverPhotoResponse;
import nasa.pojos.Photo;
import org.testng.annotations.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
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

