package nl.debijenkorf.bsl.services;

import nl.debijenkorf.bsl.core.PredefinedImageTypes;

/**
 * Created by amelingm on 16-2-2015.
 */
public interface BSLStartService {
    public String getMessage();

    public byte[] obtainImage (final PredefinedImageTypes predefinedImageTypes, final String filename, final String imageType) throws Exception;
}
