package navneet.kwickie.com.kwickiefeed.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import navneet.kwickie.com.kwickiefeed.Kwickie.Kwickie;
import navneet.kwickie.com.kwickiefeed.R;

public class KwickieAdapter extends ArrayAdapter<Kwickie> {
    private List<Kwickie> kwickieList;
    private Context ctx;

    public KwickieAdapter(Context context, int resource, List<Kwickie> kl) {
        super(context, resource, kl);
        ctx = context;
        kwickieList = kl;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_kwickie, parent, false);
        Kwickie k = kwickieList.get(position);

        // People name in Kwickie
        TextView tv = (TextView) view.findViewById(R.id.textView);
        tv.setText(k.getFirstPersonName() + " had a kwickie with " + k.getSecondPersonName());

        //Display image
        if (k.getBitmap() != null) {
            ImageView iv = (ImageView) view.findViewById(R.id.imageView);
            iv.setImageBitmap(k.getBitmap());
        }
        else {
            KwickieAndView container = new KwickieAndView();
            container.kwickie = k;
            container.view = view;

            ThumbnailLoader loader = new ThumbnailLoader();
            loader.execute(container);
        }

        return view;
    }

    class KwickieAndView {
        public Kwickie kwickie;
        public View view;
        public Bitmap bitmap;
    }

    private class ThumbnailLoader extends AsyncTask<KwickieAndView, Void, KwickieAndView> {
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected KwickieAndView doInBackground(KwickieAndView... params) {
            KwickieAndView container = params[0];
            Kwickie kwickie = container.kwickie;

            try {
                String posterURL = kwickie.getThumbnailUrl();
                InputStream in = (InputStream) new URL(posterURL).getContent();
                Bitmap bm = BitmapFactory.decodeStream(in);
                kwickie.setBitmap(bm);
                in.close();
                container.bitmap = bm;

                return container;
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(KwickieAndView result) {
            ImageView iv = (ImageView) result.view.findViewById(R.id.imageView);
            iv.setImageBitmap(result.bitmap);
            result.kwickie.setBitmap(result.bitmap);
        }
    }
}
