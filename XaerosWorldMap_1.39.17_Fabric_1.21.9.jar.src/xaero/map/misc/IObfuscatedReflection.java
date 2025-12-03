package xaero.map.misc;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public interface IObfuscatedReflection {
  Class<?> getClassForName(String paramString1, String paramString2) throws ClassNotFoundException;
  
  Field getFieldReflection(Class<?> paramClass, String paramString1, String paramString2, String paramString3, String paramString4);
  
  Method getMethodReflection(Class<?> paramClass, String paramString1, String paramString2, String paramString3, String paramString4, Class<?>... paramVarArgs);
}


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\misc\IObfuscatedReflection.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */