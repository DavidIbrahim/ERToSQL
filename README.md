# ERToSQL

## Content
- [Usage](#usage)
- [Our Team](#our-team)
- [Why use ERtoSQL?](#why-use-ertosql)
- [Algorithms](#algorithms)
- [What are the Challenges we faced?](#what-are-the-challenges-we-faced)
- [What would we like to do in the future?](#what-would-we-like-to-do-in-the-future)
## Project Description
Our android app makes defining a database easier by transforming hand-sketched ER diagrams into SQL code.
You capture the ERD, confirm it and modify the resultant SQL code.
It saves the time and effort of manually creating the database or building computerized diagrams to obtain the code.
It can also be useful in education systems to both students and instructors.
## Our Team
* [David Ibrahim Salama](https://github.com/DavidIbrahim)
* [Sara Samy Ayad](https://github.com/SaraSamyAyad)
* [Kirollos Sherif Wadie](https://github.com/KirollosWadie)
* [Rameez Barakat Hamza](https://github.com/Rameez-Barakat)
* [Martina Ihab Fouad](https://github.com/martinaihab)

## Usage
You can get your code through 5 Steps:
### 1- Draw
Draw a neat SQL Diagram containing a relationship between two entities, and the entities attributes (unique or regular)

![ERtoSQL-draw](https://user-images.githubusercontent.com/33926255/57221804-bf1a6600-7000-11e9-8f3f-e2ed1f1f684a.png)

### 2- Capture
Capture your diagram.

![ERtoSQL-capture2](https://user-images.githubusercontent.com/33926255/57222442-de19f780-7002-11e9-867a-2f6e4d0e6478.png)

### 3- Check
The captured photo will show the recognised ER shapes, If your photo is not captured correctly or not recognised, you can capture it again.

![ERtoSQL-check](https://user-images.githubusercontent.com/33926255/57222484-0c97d280-7003-11e9-8c1e-fcbc328e8527.png)

### 4- Confirm
If all ER shapes are recognised correctly, you can confirm to convert the ER diagram to SQL code.

![ERtoSQL-confirm](https://user-images.githubusercontent.com/33926255/57222544-4b2d8d00-7003-11e9-89e2-98bddc58da66.png)


### 5- Modify
The last step is naming your diagram and altering with the data types in the SQL diagram. You can also save or delete your diagram when you're finished.

![ERtoSQL-modify](https://user-images.githubusercontent.com/33926255/57222593-6ef0d300-7003-11e9-950c-e4d1975a9dc3.png)

## Why use ERtoSQL?
1- An easy-to-use app (Great UX!)

2- Database implemented with content provider to allow sharing data with other apps in the future

3 - Our App handles memory efficiently
          
          i- Uses cursor provider with Grid View for better use of RAM
          ii- Only processes the image with the highest resolution once at the beginning and saves the output SQL code with the low resolution of the image
## Algorithms
### A- Image Processing:
1- Transform image to gray-scale

2- Make some threshold

3- Make a morph-open operation with a rectangle kernel to detect rectangle:
(using contour function and count the vertices if th contour has 4 v it is a rectangle;
get the center and cut it from original image to get text from ocr)

4- Remove rectangle

5- Make a morph-open operation with a ellipse kernel to detect ellipse
(using contour function and count the vertices if th contour has more than 9 v it is an ellipse;
get the center and cut it from original image to get text from ocr)

6-Remove ellipse 

7- Detect rhombus
(using contour function and count the vertices if th contour has between 4-6 v it is a rhombus;
get the center and cut it from original image to get text from ocr)

8- Detect lines (using contour for all line  and insert them in rotating rectangle ,get the four corner of rectangle to detect start and end point of line)

9- A method loops over lines to identify what shapes they connect and it saves the shapes, at both ends of the line, in a hashmap

10- A method loops over the shapes hashmap to create the objects; ERAttributes inside EREntities, and ERRelationships between EREntities
### B-SQL Conversion:
1- After detecting all shapes, we create an ERDiagram object, containing relationships between entities.

2- The RSMapper(Relational Schema Mapper) transforms the ERDiagram into ERRelationalSchema consisting of tables, depending on the type of relationship. The tables may contain unique attributes, primary keys, foreign keys, and other regular columns.

3- The SQLMapper, then, transforms the ERRelationalSchema to a String that contains the SQL commands.

## What are the Challenges we faced?
#### 1- Differentiating between lines, and rectangles or rhombuses

          We detect rectangles and rhombuses first and erase them from the image
          After that we detect the lines
#### 2- Shapes aren't perfectly detected due to noise

          That’s why we let the user confirm it first
          We highlight the shapes for the user 
          And let him verify it for us
#### 3- OCR doesn't perfectly detect the text
          We allow the user to edit the output SQL code
#### 4- Designing the project taking into consideration the future work
## What would we like to do in the future?
1- Share the output with other apps

2- Produce a relational schema figure

3- Improve our text detection and image processing algorithm





### You can check the video to watch our live application [Here](https://www.facebook.com/kiro.wadie/videos/10215915202087735/)






          

       

