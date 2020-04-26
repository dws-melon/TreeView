package com.example.treeview;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.Log;

import java.util.LinkedList;

public class Branch {
    public static int branchColor = 0xFFFF9988;
    private PointF[] cp = new PointF[3];
    private int currentLength;
    private int maxLength;
    private float radius;
    private float part;
    private float growX;
    private float growY;
    LinkedList<Branch> childList;

    public Branch(int data[]) {
        cp[0] = new PointF(data[2],data[3]);
        cp[1] = new PointF(data[4],data[5]);
        cp[2] = new PointF(data[6],data[7]);
        radius = data[8];
        maxLength = data[9];
        part = 1.0f/maxLength;
    }

    public void addChild(Branch branch) {
        if (childList == null){
            childList = new LinkedList<>();
        }
        childList.add(branch);
    }

    public boolean grow(Canvas treeCanvas, Paint paint, int scaleFraction) {
        if (currentLength < maxLength){
            bezier(part*currentLength);
            draw(treeCanvas,paint,scaleFraction);
            currentLength++;
            radius*=0.97f;
            return true;
        }else {
            return false;
        }
    }

    private void draw(Canvas treeCanvas, Paint paint, int scaleFraction) {
        paint.setColor(branchColor);
        treeCanvas.save();
        treeCanvas.translate(growX,growY);
        treeCanvas.scale(scaleFraction,scaleFraction);
        treeCanvas.drawCircle(0,0,radius,paint);
        treeCanvas.restore();
    }

    private void bezier(float t) {
        float c0 = (1-t)*(1-t);
        float c1 = 2*t*(1-t);
        float c2 = t*t;
        growX = c0*cp[0].x + c1*cp[1].x + c2*cp[2].x;
        growY = c0*cp[0].y + c1*cp[1].y + c2*cp[2].y;
    }
}
