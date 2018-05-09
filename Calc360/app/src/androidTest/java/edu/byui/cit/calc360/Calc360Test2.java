package edu.byui.cit.calc360;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions
		.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions
		.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers
		.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;


@LargeTest
@RunWith(AndroidJUnit4.class)
public class Calc360Test2 {

	@Rule
	public ActivityTestRule<Calc360> mActivityTestRule = new
            ActivityTestRule<>(
			Calc360.class);

	@Test
	public void calc360Test2() {
		ViewInteraction calcButton = onView(
				allOf(withText("Art"),
						childAtPosition(
								allOf(withId(R.id.gridThing),
										childAtPosition(
												withClassName(
														is("android.widget.ScrollView")),
												0)),
								9)));
		calcButton.perform(scrollTo(), click());

		ViewInteraction button = onView(
				allOf(childAtPosition(
						allOf(withId(R.id.gridThing),
								childAtPosition(
										IsInstanceOf.<View>instanceOf(
												android.widget.ScrollView.class),
										0)),
						0),
						isDisplayed()));
		button.check(matches(isDisplayed()));

		ViewInteraction calcButton2 = onView(
				allOf(withText("Star Exposure"),
						childAtPosition(
								allOf(withId(R.id.gridThing),
										childAtPosition(
												withClassName(
														is("android.widget.ScrollView")),
												0)),
								0)));
		calcButton2.perform(scrollTo(), click());

		ViewInteraction appCompatEditText = onView(
				allOf(withId(R.id.focal_length_entry),
						childAtPosition(
								childAtPosition(
										withId(R.id.fragContainer),
										0),
								1),
						isDisplayed()));
		appCompatEditText.perform(replaceText("18"), closeSoftKeyboard());

		ViewInteraction appCompatEditText2 = onView(
				allOf(withId(R.id.focal_length_entry), withText("18"),
						childAtPosition(
								childAtPosition(
										withId(R.id.fragContainer),
										0),
								1),
						isDisplayed()));
		appCompatEditText2.perform(pressImeActionButton());

		ViewInteraction appCompatEditText3 = onView(
				allOf(withId(R.id.crop_factor_entry),
						childAtPosition(
								childAtPosition(
										withId(R.id.fragContainer),
										0),
								4),
						isDisplayed()));
		appCompatEditText3.perform(replaceText("1.5"), closeSoftKeyboard());

		ViewInteraction textView = onView(
				allOf(withId(R.id.starExpValField), withText("19"),
						isDisplayed()));
		//onView(withId(R.id.starExpValField)).check(matches(withText("19")));
		textView.check(matches(withText("19")));

		ViewInteraction appCompatImageButton = onView(
				allOf(withContentDescription("Navigate up"),
						childAtPosition(
								allOf(withId(R.id.toolbar),
										childAtPosition(
												withId(R.id.calc360),
												0)),
								1),
						isDisplayed()));
		appCompatImageButton.perform(click());

	}

	private static Matcher<View> childAtPosition(
			final Matcher<View> parentMatcher, final int position) {

		return new TypeSafeMatcher<View>() {
			@Override
			public void describeTo(Description description) {
				description.appendText(
						"Child at position " + position + " in parent ");
				parentMatcher.describeTo(description);
			}

			@Override
			public boolean matchesSafely(View view) {
				ViewParent parent = view.getParent();
				return parent instanceof ViewGroup && parentMatcher.matches(
						parent)
						&& view.equals(
						((ViewGroup)parent).getChildAt(position));
			}
		};
	}
}
