package com.photoeditor.holiframe;

import android.net.Uri;

public class ImagesDetails {
    String ImageName;

    public String getImageName() {
        return ImageName;
    }

    public void setImageName(String imageName) {
        ImageName = imageName;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    Uri uri;
}
