package hu.psprog.leaflet.mobile.config.dagger.module;

import android.app.Application;
import dagger.Module;
import dagger.Provides;
import hu.psprog.leaflet.mobile.R;
import ru.noties.markwon.SpannableConfiguration;
import ru.noties.markwon.il.AsyncDrawableLoader;
import ru.noties.markwon.spans.SpannableTheme;
import ru.noties.markwon.syntax.Prism4jSyntaxHighlight;
import ru.noties.markwon.syntax.Prism4jThemeDarkula;
import ru.noties.prism4j.Prism4j;
import ru.noties.prism4j.annotations.PrismBundle;

import javax.inject.Singleton;

/**
 * Dagger module for configuring spannable.
 *
 * @author Peter Smith
 */
@Module
@PrismBundle(includeAll = true)
public class SpannableConfigurationModule {

    private Application application;

    public SpannableConfigurationModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public SpannableConfiguration providesSpannableConfiguration() {
        return SpannableConfiguration.builder(application)
                .asyncDrawableLoader(AsyncDrawableLoader.create())
                .syntaxHighlight(createSyntaxHighlight())
                .theme(configureTheme())
                .build();
    }

    private Prism4jSyntaxHighlight createSyntaxHighlight() {
        return Prism4jSyntaxHighlight.create(createPrism4j(), Prism4jThemeDarkula.create());
    }

    private Prism4j createPrism4j() {
        return new Prism4j(new GrammarLocatorDef());
    }

    private SpannableTheme configureTheme() {
        return SpannableTheme.builderWithDefaults(application)
                .codeTextSize(getDimension(R.dimen.text_code_block))
                .codeBlockTextColor(getColor(R.color.codeBlockText))
                .codeBlockBackgroundColor(getColor(R.color.codeBlockBackground))
                .build();
    }

    private int getColor(int colorID) {
        return application.getResources().getColor(colorID, null);
    }

    private int getDimension(int dimensionID) {
        return application.getResources().getDimensionPixelSize(dimensionID);
    }
}
