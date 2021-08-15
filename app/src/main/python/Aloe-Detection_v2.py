import cv2 as cv
import numpy as np
from PIL import Image
import base64
import io

def preprocessor(data):
    decoded_data = base64.b64decode(data)
    np_data = np.fromstring(decoded_data, np.uint8)
    img = cv.imdecode(np_data,cv.IMREAD_UNCHANGED)
    
    contr =img.copy()
    img3 = img.copy()

    #convert image to hsv color space so light intensity is of minimal factor
    hsv = cv.cvtColor(img, cv.COLOR_BGR2HSV)


    #thresholding color into range
    minimum = np.array([40,50,40], np.uint8)
    maximum = np.array([70,255,255], np.uint8)
    thres = cv.inRange(hsv, minimum, maximum)
    
    #find contours within the thresholded
    contours, heirarchy = cv.findContours(thres, cv.RETR_TREE, cv.CHAIN_APPROX_SIMPLE)
    cv.drawContours(contr, contours, -1, (0,0,255), 2)
    
    #getting the Area of the outermost contours
    areas = [cv.contourArea(c) for c in contours]
    max_index = np.argmax(areas)
    cnt = contours[max_index]

    #getting and setting perimeters from the Area
    x,y,w,h = cv.boundingRect(cnt)
    cv.rectangle(img,(x,y),(x+w,y+h),(0,255,0),2)
    #cv.rectangle(hsv,(x,y),(x+w,y+h),(0,255,0),2)

    #image segmentation/cropping
    cropped = img3[y:y+h,x:x+w]
    pil_im=Image.fromarray(cropped)

    buff = io.BytesIO()
    pil_im.save(buff,format="PNG")
    img_str = base64.b64encode(buff.getvalue())
    return ""+str(img_str,'utf-8')


