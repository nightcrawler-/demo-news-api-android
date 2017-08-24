package demo.news.api.android.data.db;

import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;

import com.cafrecode.obviator.data.db.AppDatabase;

import org.junit.After;
import org.junit.Before;

/**
 * Created by Frederick on 24/08/2017.
 */

abstract public class DbTest {
    protected AppDatabase db;

    @Before
    public void initDb() {
        db = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                AppDatabase.class).build();
    }

    @After
    public void closeDb() {
        db.close();
    }
}

