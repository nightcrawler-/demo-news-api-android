package demo.news.api.android.data.db;

import android.support.test.runner.AndroidJUnit4;

import com.cafrecode.obviator.data.db.entities.Article;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static demo.news.api.android.LiveDataTestUtil.getValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * Created by Frederick on 24/08/2017.
 */

@RunWith(AndroidJUnit4.class)
public class ArticleDaoTest extends DbTest {

    @Test
    public void insertAndRead() throws InterruptedException {
        Article article = new Article(
                "http://www.google.com",
                "Good Day Aye",
                "Frederick N",
                "Sample article",
                "http://url.com",
                "today",
                "home");
        db.articleDao().insert(article);
        List<Article> loaded = getValue(db.articleDao().get("home"));
        Article one = loaded.get(0);

        assertThat(loaded, notNullValue());
        assertThat(one.getTitle(), notNullValue());
    }
}
