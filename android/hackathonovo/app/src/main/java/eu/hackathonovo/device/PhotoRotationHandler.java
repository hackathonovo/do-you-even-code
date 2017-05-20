package eu.hackathonovo.device;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;

import java.io.IOException;

public class PhotoRotationHandler {

    private PhotoRotationHandler(){}

    /**
     * Rotate a bitmap based on exif data.
     * @param src image path
     */
    public static Bitmap rotateBitmap(String src) throws IOException {
        Bitmap bitmap = BitmapFactory.decodeFile(src);

        ExifInterface exif = new ExifInterface(src);
        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            case ExifInterface.ORIENTATION_NORMAL:
            case ExifInterface.ORIENTATION_UNDEFINED:
            default:
                return bitmap;
        }


        Bitmap oriented = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        bitmap.recycle();
        return oriented;
    }

    /**
     * Rotate bitmap based on exif data.
     *
     * @param ctx context for finding real path from Uri.
     * @param uri uri of the photo to rotate
     * @return Rotated bitmap
     */
    public static Bitmap rotateBitmap(Context ctx, Uri uri) throws IOException {
        return rotateBitmap(PhotoRotationHandlerUtils.getRealPathFromURI(ctx, uri));
    }

    /**
     * Rotates image by degrees
     * @param src source bitmap
     * @param degrees Degrees to rotate bitmap by
     * @return Bitmap rotated by degrees.
     */
    public static Bitmap rotateBitmap(Bitmap src, float degrees){
        Matrix matrix = new Matrix();

        matrix.setRotate(degrees);

        Bitmap bitmap = Bitmap.createBitmap(src,0, 0, src.getWidth(), src.getHeight(), matrix, false);

        return bitmap;
    }

}
