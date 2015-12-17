package com.example.namit.airhockey1;

import android.content.Context;
import android.content.res.Resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by namit on 13-12-2015.
 */
public class TextResourceReader {

    public static String readTextFileFromResource(Context context,int ressourceId)
    {
        StringBuilder body = new StringBuilder();
        try
        {
            InputStream inputStream = context.getResources().openRawResource(ressourceId);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String nextLine;
            while((nextLine=bufferedReader.readLine()) != null)
            {
                body.append(nextLine);
                body.append('\n');
            }
        }
        catch (IOException e)
        {
            throw new RuntimeException("could not open resource : "+ ressourceId,e);
        }catch (Resources.NotFoundException nfe) {
            throw  new RuntimeException("Resource not found : "+ ressourceId,nfe);
        }
        return body.toString();
    }

}
