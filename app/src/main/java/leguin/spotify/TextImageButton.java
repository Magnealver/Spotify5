package leguin.spotify;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * Created by Magne on 5/20/2017.
 */

public class TextImageButton extends AppCompatButton implements Target {

    public TextImageButton(Context context) {
        super(context);
    }

    public TextImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TextImageButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        setBackgroundDrawable(new BitmapDrawable(bitmap));
    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {

    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {

    }
}
