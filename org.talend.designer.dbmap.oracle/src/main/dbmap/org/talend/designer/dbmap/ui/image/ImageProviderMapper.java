// ============================================================================
//
// Talend Community Edition
//
// Copyright (C) 2006 Talend - www.talend.com
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
//
// ============================================================================
package org.talend.designer.dbmap.ui.image;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.talend.designer.dbmap.ActivatorForDbMap;

/**
 * DOC smallet class global comment. Detailled comment <br/>
 * 
 * $Id: ImageProviderMapper.java 898 2006-12-07 11:06:17Z amaumont $
 * 
 */
public class ImageProviderMapper {

    private static Map<ImageInfo, Image> imageCache = new HashMap<ImageInfo, Image>();

    public static Image getImage(ImageDescriptor desc) {
        return desc.createImage();
    }

    public static Image getImage(ImageInfo imageInfo) {
        Image imageFromCache = imageCache.get(imageInfo);
        if (imageFromCache != null) {
            return imageFromCache;
        }
        Image image = getImage(getImageDescriptor(imageInfo));
        imageCache.put(imageInfo, image);
        return image;
    }

    public static ImageDescriptor getImageDescriptor(ImageInfo image) {
        return ImageDescriptor.createFromFile(ActivatorForDbMap.class, image.getPath());
    }

    /**
     * You can continue to use the provider after call this method.
     */
    public static void releaseImages() {
        Collection<Image> images = imageCache.values();
        for (Image image : images) {
            if (!image.isDisposed()) {
                image.dispose();
            }
        }
        imageCache.clear();
    }

    public static void dispose(ImageInfo imageInfo) {
        Image image = imageCache.get(imageInfo);
        if (!image.isDisposed()) {
            image.dispose();
        }
        imageCache.remove(imageInfo);
    }

}
