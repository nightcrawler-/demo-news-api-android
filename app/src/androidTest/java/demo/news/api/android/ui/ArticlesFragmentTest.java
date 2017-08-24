package demo.news.api.android.ui;

import android.arch.lifecycle.MutableLiveData;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.cafrecode.obviator.data.db.entities.Article;

import org.junit.Rule;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import demo.news.api.android.R;
import demo.news.api.android.ViewModelUtil;
import demo.news.api.android.data.db.entities.Resource;
import demo.news.api.android.data.db.entities.Status;
import demo.news.api.android.data.viewmodels.ArticleViewModel;
import demo.news.api.android.ui.fragments.ArticlesFragment;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Frederick on 24/08/2017.
 */

@RunWith(AndroidJUnit4.class)
public class ArticlesFragmentTest {

    @Rule
    public ActivityTestRule<HomeActivity> activityRule =
            new ActivityTestRule<>(HomeActivity.class, true, true);
    private MutableLiveData<Resource<List<Article>>> articles = new MutableLiveData<>();

    private ArticlesFragment articlesFragment;
    private ArticleViewModel viewModel;

    public void init() {
        articlesFragment = ArticlesFragment.newInstance("home");
        viewModel = mock(ArticleViewModel.class);

        when(viewModel.list(null)).thenReturn(articles);
        articlesFragment.viewModelFactory = ViewModelUtil.createFor(viewModel);

        activityRule.getActivity().replaceFragment(articlesFragment, "Articles");
    }

    public void testLoading() {
        articles.postValue(Resource.loading(null));
        onView(withId(R.id.progressBar2)).check(matches(isDisplayed()));
    }

    public void testLoaded() throws InterruptedException {
        articles.postValue(new Resource<>(Status.SUCCESS, genArticles(), ""));
        onView(withId(R.id.progressBar2)).check(matches(not(isDisplayed())));
    }

    private List<Article> genArticles() {
        Article article = new Article(
                "http://www.google.com",
                "Good Day Aye",
                "Frederick N",
                "Sample article",
                "http://url.com",
                "today",
                "home");

        List<Article> articles = new ArrayList<>();
        articles.add(article);


        return articles;
    }
}
