package com.example.namit.airhockey1;

/**
 * Created by namit on 13-12-2015.
 */

import android.content.Context;
import android.graphics.Shader;
import android.opengl.GLSurfaceView;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


import static android.opengl.GLES20.*;

public class AirHockeyRenderer implements GLSurfaceView.Renderer{
    private final Context context;
    private static final int POSITION_COMPONENT_COUNT=2;
    private static final int BYTES_PER_FLOAT=4;
    private final FloatBuffer vertexData;

    private int program;

    private static final String U_COLOR = "u_Color";
    private int uColorLocation;

    private static final String A_POSITION = "a_Position";
    private int aPositionLocation;

    AirHockeyRenderer(Context context)
    {
        this.context = context;
        float[] tableVerticesWithTriangles = {
                //triangle 1
                -0.5f,-0.5f,
                0.5f,0.5f,
                -0.5f,0.5f,

                // triangle 2
                -0.5f,-0.5f,
                0.5f,-0.5f,
                0.5f,0.5f,

                // line 1
                -0.5f,0f,
                0.5f,0f,

                // mallets
                0f,-0.25f,
                0f,0.25f
        };
        vertexData = ByteBuffer.allocateDirect(tableVerticesWithTriangles.length * BYTES_PER_FLOAT).order(ByteOrder.nativeOrder()).asFloatBuffer();

        vertexData.put(tableVerticesWithTriangles);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        glClearColor(0.0f,0.0f,0.0f,0.0f);
        String vertexShaderSource = TextResourceReader.readTextFileFromResource(context,R.raw.simple_vertex_shader);

        String fragmentShaderSource = TextResourceReader.readTextFileFromResource(context,R.raw.simple_fragment_shader);

        int vertexShader = ShaderHelper.compileVertexShader(vertexShaderSource);
        int fragmentShader = ShaderHelper.compileFragmentShader(fragmentShaderSource);

        program= ShaderHelper.linkProgram(vertexShader,fragmentShader);

        if(LoggerConfig.ON)
        {
            ShaderHelper.validateProgram(program);
        }

        glUseProgram(program);

        uColorLocation = glGetUniformLocation(program,U_COLOR);

        aPositionLocation=glGetAttribLocation(program, A_POSITION);

        vertexData.position(0);

        glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GL_FLOAT, false, 0, vertexData);
        glEnableVertexAttribArray(aPositionLocation);

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        glViewport(0,0,width,height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        glClear(GL_COLOR_BUFFER_BIT);

        //  Log.e("namit","namit draw");

        glUniform4f(uColorLocation,1.0f,1.0f,1.0f,1.0f);
        glDrawArrays(GL_TRIANGLES, 0, 6);

        glUniform4f(uColorLocation,1.0f,0.0f,0.0f,1.0f);
        glDrawArrays(GL_LINES, 6, 2);

        glUniform4f(uColorLocation, 0.0f, 0.0f, 1.0f, 1.0f);
        glDrawArrays(GL_POINTS, 8, 1);

        glUniform4f(uColorLocation, 1.0f, 0.0f, 0.0f, 1.0f);
        glDrawArrays(GL_POINTS,9,1);



    }
}
