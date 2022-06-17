import com.rongmei.MainApplication;
import com.rongmei.bl.upload.ImageUploadBlServiceImpl;
import com.rongmei.exception.SystemException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ImgTest {

    @Autowired
    private ImageUploadBlServiceImpl imageUploadBlService;


  /*  @Autowired
    public ImgTest (ImageUploadBlServiceImpl imageUploadBlService){
        this.imageUploadBlService = imageUploadBlService;
    }*/

    public ImgTest ( ){
    }


    @Test
    public void downFileTest(){
      /*  try {
        String url = "2021_05_31_11_55_54_0357_微信图片_20201119031344.jpg";
            imageUploadBlService.down(url);
            System.out.println(1);
        } catch (SystemException e) {
            e.printStackTrace();
            System.out.println(2);
        }*/
    }
}
