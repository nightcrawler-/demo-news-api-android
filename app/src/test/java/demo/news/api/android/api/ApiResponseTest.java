package demo.news.api.android.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import demo.news.api.android.data.api.ApiResponse;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Response;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Frederick on 24/08/2017.
 */
@RunWith(JUnit4.class)
public class ApiResponseTest {

    @Test
    public void exception() {
        Exception exception = new Exception("problem");
        ApiResponse<String> apiResponse = new ApiResponse<>(exception);
        assertThat(apiResponse.getBody(), nullValue());
        /*
        assertThat(String.valueOf(apiResponse.getCode()), equals(500));
        assertThat(apiResponse.getErrorMessage(), equals("problem"));
        */
    }

    @Test
    public void success() {
        ApiResponse<String> apiResponse = new ApiResponse<>(Response.success("foo"));
        assertThat(apiResponse.getErrorMessage(), nullValue());
        /*
        assertThat(String.valueOf(apiResponse.getCode()), equals(200));
        assertThat(apiResponse.getBody(), equals("foo"));
        */
    }


    @Test
    public void error() {
        ApiResponse<String> response = new ApiResponse<String>(Response.error(400,
                ResponseBody.create(MediaType.parse("application/txt"), "error")));
        /*
        assertThat(String.valueOf(response.getCode()), equals(400));
        assertThat(response.getErrorMessage(), equals("error"));
        */
    }
}

