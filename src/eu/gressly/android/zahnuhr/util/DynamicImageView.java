package eu.gressly.android.zahnuhr.util;
/**
 * @author  phi (phi@gressly.eu)
 * @version alpha
 * @date    16. 12. 2013
 * 
 * @purpose Scales an image to about 75% in height, but only, if the
 *          tablet (phone) is in landscape mode.
 */
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Draws an image in landscape layout 75% of the screen width.
 * @version 0.1 (Jul 31, 2014)
 * @author Philipp Gressly Freimann 
 *         (philipp.gressly@santis.ch)
 */
public class DynamicImageView extends ImageView {
	public static final float HEIGHT_PERCENTAGE_IN_LANDSCAPE_LAYOUT = 0.75f;

	private static DynamicImageView oldView = null;

	public DynamicImageView(final Context context, final AttributeSet attrs) {
		super(context, attrs);
		if(null != oldView) {
			oldView.setImageResource(0);
		}
		oldView = this;
		System.out.println("DEBUG Dynamic Image View Constructor");
	}


	@Override
	protected void onMeasure(final int widthMeasureSpec,
			final int heightMeasureSpec) {
		final Drawable thisDrawable = this.getDrawable();

		if (null != thisDrawable) {
			// ceil not round - avoid thin vertical gaps along the left/right
			// edges
			if(breitbild(widthMeasureSpec, heightMeasureSpec)) {
				final int height = (int) (HEIGHT_PERCENTAGE_IN_LANDSCAPE_LAYOUT * MeasureSpec.getSize(heightMeasureSpec)); 
				final int width = (int) Math.ceil(height
						* (float) thisDrawable.getIntrinsicWidth() / thisDrawable.getIntrinsicHeight());
				this.setMeasuredDimension(width, height);

			} else {
				final int width = MeasureSpec.getSize(widthMeasureSpec);
				final int height = (int) Math.ceil(width
						* (float) thisDrawable.getIntrinsicHeight() / thisDrawable.getIntrinsicWidth());
				this.setMeasuredDimension(width, height);
			}
		} else {
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		}
	}


	private boolean breitbild(int widthMeasureSpec, int heightMeasureSpec) {
		if(null == AllActivities.getNewestSlideActivity()) {
			return false;
		}
		return Configuration.ORIENTATION_LANDSCAPE ==
		       AllActivities.getNewestSlideActivity().getResources().getConfiguration().orientation;
		// other possibility? :
		//return MeasureSpec.getSize(widthMeasureSpec) > MeasureSpec.getSize(heightMeasureSpec);
	}

} // end class DynamicImageView