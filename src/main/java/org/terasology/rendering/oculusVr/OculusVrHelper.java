/*
 * Copyright 2012
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.terasology.rendering.oculusVr;

import org.terasology.TeraOVR;

/**
 * Helper class for the Oculus Rift.
 *
 * @author Benjamin Glatzel <benjamin.glatzel@me.com>
 */
public class OculusVrHelper {

    private static float verticalRes = 800.0f;
    private static float horizontalRes = 1280.0f;
    private static float verticalScreenSize = 0.0935f;
    private static float horizontalScreenSize = 0.14976f;
    private static float eyeToScreenDistance = 0.041f;
    private static float lensSeparationDistance = 0.0635f;
    private static float interpupillaryDistance = 0.064f;
    private static final float[] distortionParams = { 1.0f, 0.22f, 0.24f, 0.0f };

    private static float halfScreenDistance;
    private static float viewCenter;
    private static float aspectRatio;
    private static float eyeProjectionShift;
    private static float projectionCenterOffset;
    private static float lensOffset;
    private static float lensShift;
    private static float lensViewportShift;
    private static float scaleFactor;
    private static float percievedHalfRTDistance;
    private static float yFov;

    public static boolean nativeLibraryIsLoaded = false;

    static {
        updateHelperVariables();
    }

    public static void updateFromDevice() {
        verticalRes = TeraOVR.getVResolution();
        horizontalRes = TeraOVR.getHResolution();
        verticalScreenSize = TeraOVR.getVScreenSize();
        horizontalScreenSize = TeraOVR.getHScreenSize();
        eyeToScreenDistance = TeraOVR.getEyeToScreenDistance();
        lensSeparationDistance = TeraOVR.getLensSeparationDistance();
        interpupillaryDistance = TeraOVR.getInterpupillaryDistance();

        distortionParams[0] = TeraOVR.getDistortitionK0();
        distortionParams[1] = TeraOVR.getDistortitionK1();
        distortionParams[2] = TeraOVR.getDistortitionK2();
        distortionParams[3] = TeraOVR.getDistortitionK3();

        updateHelperVariables();
    }

    private static void updateHelperVariables() {
        halfScreenDistance = horizontalScreenSize * 0.5f;
        aspectRatio = (horizontalRes * 0.5f) / verticalRes;
        viewCenter = horizontalScreenSize * 0.25f;
        eyeProjectionShift = viewCenter - lensSeparationDistance * 0.5f;
        projectionCenterOffset = 4.0f * eyeProjectionShift / horizontalScreenSize;

        lensOffset = lensSeparationDistance * 0.5f;
        lensShift =  horizontalScreenSize * 0.25f - lensOffset;
        lensViewportShift =  4.0f * lensShift / horizontalScreenSize;

        scaleFactor = 1.6f;
        percievedHalfRTDistance = (verticalScreenSize / 2) * scaleFactor;
        yFov = 2.0f * (float) Math.atan(percievedHalfRTDistance / eyeToScreenDistance);
        //yFov = 2.0f * (float) Math.atan(halfScreenDistance/eyeToScreenDistance);
    }

    public static float getVerticalRes() {
        return verticalRes;
    }

    public static float getHorizontalRes() {
        return horizontalRes;
    }

    public static float getVerticalScreenSize() {
        return verticalScreenSize;
    }

    public static float getHorizontalScreenSize() {
        return horizontalScreenSize;
    }

    public static float getEyeToScreenDistance() {
        return eyeToScreenDistance;
    }

    public static float getLensSeparationDistance() {
        return lensSeparationDistance;
    }

    public static float getInterpupillaryDistance() {
        return interpupillaryDistance;
    }

    public static float[] getDistortionParams() {
        return distortionParams;
    }

    public static float getHalfScreenDistance() {
        return halfScreenDistance;
    }

    public static float getViewCenter() {
        return viewCenter;
    }

    public static float getAspectRatio() {
        return aspectRatio;
    }

    public static float getEyeProjectionShift() {
        return eyeProjectionShift;
    }

    public static float getProjectionCenterOffset() {
        return projectionCenterOffset;
    }

    public static float getLensOffset() {
        return lensOffset;
    }

    public static float getLensShift() {
        return lensShift;
    }

    public static float getLensViewportShift() {
        return lensViewportShift;
    }

    public static float getScaleFactor() {
        return scaleFactor;
    }

    public static float getPercievedHalfRTDistance() {
        return percievedHalfRTDistance;
    }

    public static float getyFov() {
        return yFov;
    }
}