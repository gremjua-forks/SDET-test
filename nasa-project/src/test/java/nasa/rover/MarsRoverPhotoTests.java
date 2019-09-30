package nasa.rover;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import nasa.pojos.MarsRoverPhotoResponse;
import nasa.pojos.Photo;
import org.testng.annotations.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;



public class MarsRoverPhotoTests {

    @Test
    public void verifyPhotosBySolEqualPhotosByEarthDate(){
        MarsRoverPhotoService marsRoverPhotoService = new MarsRoverPhotoService(System.getenv("API_KEY"));
        marsRoverPhotoService.setRover("curiosity");
        marsRoverPhotoService.setSol(1000);
        MarsRoverPhotoResponse responseBySol = marsRoverPhotoService.getPhotosBySol();
        List<Photo> photosBySol = responseBySol.getPhotos().stream().limit(10).collect(Collectors.toCollection(LinkedList::new));

        marsRoverPhotoService.setEarthDate("2015-05-30");
        MarsRoverPhotoResponse responseByEarthDate = marsRoverPhotoService.getPhotosByEarthDate();
        List<Photo> photosByEarthDate = responseByEarthDate.getPhotos().stream().limit(10).collect(Collectors.toCollection(LinkedList::new));

        assertThat(photosBySol,is(equalTo(photosByEarthDate)));
    }
}

