package com.sf.core;


import org.objectweb.asm.*;

import java.io.FileOutputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.security.ProtectionDomain;

import static jdk.internal.org.objectweb.asm.Opcodes.*;

/**
 * Created with IntelliJ IDEA.
 * User: qusifan
 * Date: 2018/4/28
 * Time: 下午3:55
 */
public class MyTransformer implements ClassFileTransformer {

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if(className.contains("com/sf/service")){
            return transformClass(loader,className,classBeingRedefined,protectionDomain,classfileBuffer);
        }
        return classfileBuffer;
    }

    private byte[] transformClass(ClassLoader loader, String className,final Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {
        ClassReader classReader = new ClassReader(classfileBuffer);
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        classReader.accept(new ClassVisitor(Opcodes.ASM4,classWriter) {
            @Override
            public MethodVisitor visitMethod(final int access,
                                             final String name, final String desc,
                                             final String signature, final String[] exceptions) {
                MethodVisitor mv = super.visitMethod(access, name,
                        desc, signature, exceptions);
                if ("<init>".equals(name))
                    return mv;

                return new MethodVisitor(Opcodes.ASM4, mv) {

                    public void visitCode() {
                        super.visitCode();
//                        int paramSize = getParamSize(desc);
//                        mv.visitIntInsn(BIPUSH,paramSize);
//                        mv.visitTypeInsn(ANEWARRAY, Type.getInternalName(Object.class));
//                        mv.visitInsn(DUP);
//                        mv.visitInsn(ICONST_0);
//                        mv.visitLdcInsn(name);
//                        mv.visitInsn(AASTORE);
//                        mv.visitInsn(DUP);
//                        mv.visitInsn(ICONST_1);
//                        mv.visitVarInsn(ALOAD,1);
//                        mv.visitInsn(AASTORE);
//                        mv.visitInsn(DUP);
//                        mv.visitInsn(ICONST_2);
//                        mv.visitVarInsn(ALOAD,2);
//                        mv.visitInsn(AASTORE);
//                        mv.visitMethodInsn(INVOKESTATIC, "com/sf/plugins/aop/AopInterceptor", "beforeInvoke", "([Ljava/lang/Object;)V",false);
                        invokeStatic(mv,desc,name);
                    }
                    public void visitInsn(int opcode) {
                        if (opcode == RETURN) {//在返回之前安插after 代码。
                            mv.visitMethodInsn(INVOKESTATIC, "com/sf/plugins/aop/AopInterceptor", "afterInvoke", "()V",false);
                        }
                        mv.visitInsn(opcode);
                    }

//                    public void visitMaxs(int maxStack,int maxLocals){
//                        System.out.println("maaaaaaa");
//                        mv.visitMaxs(Math.max(EXCEPTION_STACK,maxStack), maxLocals);
//                    }
                };
            }
        }, 0);
        byte[] bytes = classWriter.toByteArray();
        writeto(bytes,className);
        return bytes;
    }



    private void invokeStatic(MethodVisitor mv,String methodDesc,String methodName){
        int paramSize = getParamSize(methodDesc);
        mv.visitIntInsn(BIPUSH,paramSize);
        mv.visitTypeInsn(ANEWARRAY, Type.getInternalName(Object.class));
        mv.visitInsn(DUP);
        mv.visitInsn(ICONST_0);
        mv.visitLdcInsn(methodName);
        mv.visitInsn(AASTORE);
        for(int i = 1;i<paramSize;i++){
            mv.visitInsn(DUP);
            mv.visitIntInsn(BIPUSH,i);
            mv.visitVarInsn(ALOAD,i);
            mv.visitInsn(AASTORE);
        }
        mv.visitMethodInsn(INVOKESTATIC, "com/sf/plugins/aop/AopInterceptor", "beforeInvoke", "([Ljava/lang/Object;)V",false);
    }


    private int getParamSize(String desc){
        return desc.split(";").length;
    }


    public static void writeto(byte[] by,String name){
        String newname = name.substring(name.lastIndexOf("/"),name.length());
        try(FileOutputStream f = new FileOutputStream("/Users/qusifan/Downloads/pinpoint-1.7.2/mypinpoint/target"+
                newname+"new.class")){
            f.write(by);
            f.flush();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
