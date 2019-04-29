/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

// https://github.com/Team303/Java-Robot-Project-2017/blob/master/src/org/usfirst/frc/team303/robot/Camera.java

package frc.robot.vision;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.vision.GripPipeline;

/**
 * Add your docs here.
 */
public class ProcessVision {
    private static final int IMG_WIDTH = 320;
    private static final int IMG_HEIGHT = 240;
    private Thread visionThread;
    private GripPipeline pipeline;
    private double centerXOne = 0.0;
	private double centerYOne = 0.0;
	private double centerXTwo = 0.0;
	private double centerYTwo = 0.0;
	private double centerXAvg = 0.0;
	private double centerYAvg = 0.0;
    private double rectangleArea=0.0;
    private boolean runProcessing = true;
    public final Object imgLock = new Object();

    public ProcessVision() {
        pipeline = new GripPipeline();
        UsbCamera camera = CameraServer.getInstance().startAutomaticCapture(0);
        camera.setResolution(IMG_WIDTH, IMG_HEIGHT);

        CvSink cvSink = CameraServer.getInstance().getVideo(); //capture mats from camera
		CvSource outputStream = CameraServer.getInstance().putVideo("Stream", IMG_WIDTH, IMG_HEIGHT); //send steam to CameraServer
		Mat mat = new Mat(); //define mat in order to reuse it

        visionThread = new Thread(() -> {

			while(!Thread.interrupted()) { //this should only be false when thread is disabled
				if(cvSink.grabFrame(mat)==0) { //fill mat with image from camera)
					outputStream.notifyError(cvSink.getError()); //send an error instead of the mat
					SmartDashboard.putString("Vision State", "Acquisition Error");
					continue; //skip to the next iteration of the thread
                }
                
                if (runProcessing) {
                    pipeline.process(mat); //process the mat (this does not change the mat, and has an internal output to pipeline)

                    if (!pipeline.filterContoursOutput().isEmpty()) {
                        int contoursFound = pipeline.filterContoursOutput().size();
                        SmartDashboard.putNumber("Vision Contours", contoursFound);
                        if (contoursFound == 2) {
                            Rect r1 = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
                            Rect r2 = Imgproc.boundingRect(pipeline.filterContoursOutput().get(1));
                            
                            //sort the rectangles horizontally
						    Rect rectLeft = (r1.x<r2.x) ? r1 : r2;
						    Rect rectRight = (r1.x>r2.x) ? r1 : r2;		
						    r1 = rectRight;
                            r2 = rectLeft;
                        
                            //calculate center X and center Y pixels
						    centerXOne = r1.x + (r1.width/2); //returns the center of the bounding rectangle
						    centerYOne = r1.y + (r1.height/2); //returns the center of the bounding rectangle
						    centerXTwo = r2.x + (r2.width/2);
						    centerYTwo = r2.y + (r2.height/2);
						
						    double width=r2.x-(r1.x+r1.width);
						    double height=r1.y-(r2.y+r2.height);

						    rectangleArea=width*height;
						    centerYAvg = (centerYOne + centerYTwo)/2;
						    centerXAvg = (centerXOne + centerXTwo)/2;
		
						    //draws the rectangles onto the camera image sent to the dashboard
						    Imgproc.rectangle(mat, new Point(r1.x, r1.y), new Point(r2.x + r2.width, r2.y + r2.height), new Scalar(0, 0, 255), 2); 
						    Imgproc.rectangle(mat, new Point(centerXAvg-3,centerYAvg-3), new Point(centerXAvg+3,centerYAvg+3), new Scalar(255, 0, 0), 3);
                        }
                        SmartDashboard.putNumber("Center X", centerXAvg);
                    }
                }
                outputStream.putFrame(mat);
            }
        });
        visionThread.setDaemon(true);
        visionThread.start();
    }

    public void driveVision() {
        double turn = centerXAvg - (IMG_WIDTH / 2);
        Robot.driveTrain.setRampArcadeVolts(-0.6, turn * 0.005);
    }

    public double getArea(){
		return rectangleArea;
	}

	public double getCenterY() {
		return centerYAvg;
	}

	public double getCenterX() {
		return centerXAvg;
	}

	public void disableProcessing() {
		runProcessing = false;
	}

	public void enableProcessing() {
		runProcessing = true;
	}

}
