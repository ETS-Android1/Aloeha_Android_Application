# Aloeha_Android_Application
UI manager and Android Layer of Aloeha. 

Aloeha is a thesis project conducted by me and my groupmates. It uses a novel computer vision algorithm called Capsule Neural Networks to 
identify leaf rot and rust in Aloe Vera plants. This Repo contains the final product of our thesis: An android app that uses capsnet to identify Aloe Vera leaf rot and rust. The app is **93% accurate** 

The Capsule Neural Network model used in this app is discussed in more detail [here.](https://github.com/Jedflo/Aloeha_capsule_neural_networks)

# Functional Requirements
- [x] able to use the phone's camera as an input method
- [x] able to use an existing photos via phone gallery as input method
- [x] able to pass the user's selected image to the Preprocessing manager (written in Python)
- [x] able to get the outputs of the capsnet manager (written in python)
  - [x] get the reconstruction of the input image
  - [x] get the classification of the input image   
- [x] able to dispaly the classification and reconstruction of the input image to the user'

# Aloeha App Design 

![image](https://user-images.githubusercontent.com/82581503/162132072-8f3ad615-0a54-425f-ac8b-da9655dbf526.png)
The image above shows a flowchart of The application's activities. 

### Step 1
![image](https://user-images.githubusercontent.com/82581503/162135510-62ade61e-3a3e-4d14-9bc8-ac7ffb601cc9.png)

The first activity the user will interact with once the app is opened is the Startup page or main activity. Here, the user can choose to input an Aloe Vera leaf image to the app by: 
- taking a picture through the phone's camera by tapping the button with the camera icon or 
- selecting an already existing picture through the phone's gallery by tapping the button with the gallery icon. 

### Step 2 (Camera)
![image](https://user-images.githubusercontent.com/82581503/162135943-ce2909ab-3477-45d9-aef8-7fb50413e1ad.png)

If the user wants to input via camera, the main activity will start a new camera activity where the user can use the phone's camera to take a picture of an aloe vera leaf.

![image](https://user-images.githubusercontent.com/82581503/162134894-ea9d24ef-f9a5-4e4d-97d6-e5d7504c36da.png)

Once the user has taken a picture, the camera activity will show the picture taken. the user can proceed with classification by tapping the classify button or choose to retake the image by tapping the cancel button. When the user chooses to proceed with classification, the Output activity will start.

### Step 2 (Gallery)
![image](https://user-images.githubusercontent.com/82581503/162137233-4773fb1b-3487-44f3-817f-7536232e9113.png)

If the user wants to input via gallery, the main activity will start a new gallery activity where the user can select an already existing picture of an aloe vera leaf. once the user has tapped a picture within the gallery activity, that picture is selected for classification and the output activity will start.

### Step 3 
![image](https://user-images.githubusercontent.com/82581503/162136130-2214f4d3-4aac-4ecb-85a7-f64aa973e7ad.png)

The third and final step is the same for both input methods, it is the output activity. The output activity displays the input image of the user, the reconstruction of the input image by capsnet, the input image's classification, and if it is classified as rot or rust; its possible causes and treatments. 

# Aloeha App Architecture

![image](https://user-images.githubusercontent.com/82581503/162374223-171248cb-2d36-41a3-80a9-e448e2b32c62.png)

Shown above is a rough diagram of the application's architecture. 

# Aloeha App Code
## Java
### Splash Activity
Serves as the splash screen of the app. If the app is opened, it will display the splash screen (shown below) for 3 seconds.
![image](https://user-images.githubusercontent.com/82581503/162375593-bff291e8-d2c6-40ca-99c2-35f9727b95a0.png)

### Main Activity
This is where the user can choose a method of input. This file contains the code to ask android to give the app permission to access camera and storage, and the logic to trigger the camera or gallery intent. 

### Output Activity
This file is responsible for collecting the input image from the main activity, starting the python interpreter, passing the input image to the python interpreter, getting the output of the python interpreter (or capsnet manager.), and displaying the results to the user. 

## Python
### trained_model.h5
Contains the weights of the capsnet model. Produced during training (training details can be seen [here](https://github.com/Jedflo/Aloeha_capsule_neural_networks))

### Capsnet_Model.py
Contains the capsnet model as described [here](https://github.com/Jedflo/Aloeha_capsule_neural_networks). 

### Capsnet_Manager.py
This contains a function that loads the weights and input image to the capsnet model. Basically, this contains the capsnet classifier function of the system.


### preprocessor.py
This file contains useful image preprocessing functions, specifically a crop function made by [Mathew Mojado](https://github.com/MachuMachu)

### Preprocessing_Manager.py
This file contains a function that crops (from preprocessor.py) and resizes images to 32x32 pixels then changes their color mode to HSV. 

### Capsnet_main.py
This file contains a function that connects the Preprocesssing Manager to the Capsnet Manager. When the java application starts the python interpreter, the *Capsnet* function within this file is executed. The said function takes in an image as an arguement and returns two strings; the classification of the input image, and the reconstruction of the input image converted to string64 format.


# Capsule Neural Networks for the Aloe Vera Affliction Dataset
To see the developmental details of the Capsule Neural Network used within this app click [here](https://github.com/Jedflo/Aloeha_capsule_neural_networks)








