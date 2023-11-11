package org.sample.Process;

import cn.hutool.core.collection.LineIter;
import org.sample.Entity.Position;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import static cn.hutool.core.io.IoUtil.lineIter;

public class DataReader {

    public static String getDataPath(String str){
        return Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource(str+".txt")).getPath();
    }

    public static ArrayList<Position> read(String sign){
        ArrayList<Position> positions = new ArrayList<>();
        try(InputStream inputStream = new BufferedInputStream(new FileInputStream(getDataPath(sign)))){
            LineIter iter = lineIter(inputStream, Charset.defaultCharset());
            while(iter.hasNext()) {
                positions.add(transToPosition(iter.next()));
            }
            return positions;
        } catch (IOException ignored) {}
        return null;
    }

    public static Position transToPosition(String str){
        String[] list = str.split(" ");
        return new Position(Arrays.stream(list).mapToDouble(Double::parseDouble).toArray());
    }
}
