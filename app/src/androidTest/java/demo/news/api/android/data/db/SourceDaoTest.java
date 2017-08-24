package demo.news.api.android.data.db;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import demo.news.api.android.data.db.entities.Source;

import static demo.news.api.android.LiveDataTestUtil.getValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * Created by Frederick on 24/08/2017.
 */

@RunWith(AndroidJUnit4.class)
public class SourceDaoTest extends DbTest {

    @Test
    public void insertAndRead() throws InterruptedException {
        Source source = new Source(
                "http://www.google.com",
                "Source",
                "Desc",
                "internet",
                "http://url.com");

        List<Source> sources = new ArrayList<>();
        sources.add(source);

        db.sourceDao().insert(sources);

        List<Source> loaded = getValue(db.sourceDao().list());
        Source one = loaded.get(0);

        assertThat(loaded, notNullValue());
        assertThat(one.getName(), notNullValue());
    }
}
