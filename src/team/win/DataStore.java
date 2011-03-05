package team.win;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Point;

public class DataStore {
	private List<Primitive> mPrimitiveList;
	private int mWidth;
	private int mHeight;

	public DataStore() {
		super();
		mPrimitiveList = new LinkedList<Primitive>();
	}

	public boolean add(Primitive p) {
		return mPrimitiveList.add(p);
	}

	public Primitive remove(int index) {
		return mPrimitiveList.remove(index);
	}

	public int size() {
		return mPrimitiveList.size();
	}

	public String getAllPrimitivesAsJSON() {
		try {
			JSONArray primitives = new JSONArray();
			for (Primitive primitive : mPrimitiveList) {
				JSONArray pointArray = new JSONArray();
				for (Point point : primitive.mPoints) {
					pointArray.put(point.x);
					pointArray.put(point.y);
				}
				JSONObject primObject = new JSONObject();
				// Format color and mask out alpha channel
				primObject.put("color", Integer.toHexString(primitive.mColor));
				primObject.put("strokeWidth", primitive.mStrokeWidth);
				primObject.put("points", pointArray);
				primitives.put(primObject);
			}
			JSONObject o = new JSONObject();
			o.put("width", mWidth);
			o.put("height", mHeight);
			o.put("primitives", primitives);
			return o.toString();
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	public void setWidth(int width) {
		mWidth = width;
	}
	
	public void setHeight(int height) {
		mHeight = height;
	}
}
