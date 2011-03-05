package team.win;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DataStore {
	private LinkedList<Primitive> mPrimitiveList;
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

	public void setWidth(int width) {
		mWidth = width;
	}

	public void setHeight(int height) {
		mHeight = height;
	}

	public String getAllPrimitivesAsJSON() {
		try {
			JSONArray primitives = new JSONArray();
			for (Primitive primitive : mPrimitiveList) {
				JSONArray pointArray = new JSONArray();
				for (Point point : primitive.mPoints) {
					pointArray.put(point.mX);
					pointArray.put(point.mY);
				}
				JSONObject primObject = new JSONObject();
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

	public void serializeDataStore(OutputStream outputStream) throws IOException {
		new ObjectOutputStream(outputStream).writeObject(mPrimitiveList);
	}

	@SuppressWarnings("unchecked")
	public void deserializeDataStore(InputStream inputStream) throws IOException {
		try {
			mPrimitiveList = (LinkedList<Primitive>)new ObjectInputStream(inputStream).readObject();
			System.out.println("Loaded prims");
			for (Primitive p : mPrimitiveList)
				System.out.println(p.toString());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
