package com.example.namit.airhockey1;

/**
 * Created by namit on 13-12-2015.
 */
import android.util.Log;

import static android.opengl.GLES20.*;
public class ShaderHelper {
    public static final String TAG = "ShaderHelper";
    public static  int compileVertexShader(String shaderCode)
    {
        return compileShader(GL_VERTEX_SHADER,shaderCode);
    }

    public static  int compileFragmentShader(String shaderCode)
    {
        return compileShader(GL_FRAGMENT_SHADER, shaderCode);
    }

    private static int compileShader(int type,String Shadercode)
    {
        final int  shaderObjectID = glCreateShader(type);

        if(shaderObjectID == 0)
        {
            if(LoggerConfig.ON)
            {
                Log.w(TAG, "Could not create new shader");
            }
            return 0;
        }
        glShaderSource(shaderObjectID, Shadercode);
        glCompileShader(shaderObjectID);

        final int[] compileStatus = new int[1];
        glGetShaderiv(shaderObjectID,GL_COMPILE_STATUS,compileStatus,0);
        if(LoggerConfig.ON)
        {
            Log.v(TAG,"Results of compiling source :"+ Shadercode + "\n:"+ glGetShaderInfoLog(shaderObjectID));
        }
        if(compileStatus[0]==0)
        {
            glDeleteShader(shaderObjectID);
            if(LoggerConfig.ON)
            {
                Log.e(TAG,"Compilation of shader failed");
            }
            return 0;
        }
        else
        {
            return shaderObjectID;
        }

    }

    public static int linkProgram(int vertexShaderId,int fragmentShaderID)
    {
        int programeObjectId = glCreateProgram();

        if(programeObjectId==0)
        {
            if(LoggerConfig.ON)
            {
                Log.e(TAG,"Could not create new program");
            }
            return 0;
        }

        glAttachShader(programeObjectId, vertexShaderId);
        glAttachShader(programeObjectId, fragmentShaderID);
        glLinkProgram(programeObjectId);

        final int[] linkStatus = new int[1];
        glGetProgramiv(programeObjectId,GL_LINK_STATUS,linkStatus,0);
        if(LoggerConfig.ON)
        {
            Log.e(TAG,"results of linking program : \n"+glGetProgramInfoLog(programeObjectId));
        }
        if(linkStatus[0]==0)
        {
            glDeleteProgram(programeObjectId);

            if(LoggerConfig.ON)
            {
                Log.e(TAG,"linking of pgm failed");
            }
            return 0;
        }
        return programeObjectId;
    }

    public static boolean validateProgram(int programObjectId)
    {
        glValidateProgram(programObjectId);

        final int[] validateStatus = new int[1];
        glGetProgramiv(programObjectId,GL_VALIDATE_STATUS,validateStatus,0);
        Log.v(TAG,"Results of validating program "+validateStatus[0] + "\n Log" + glGetProgramInfoLog(programObjectId));

        return validateStatus[0] != 0;
    }

}
