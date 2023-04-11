package com.epam.mjc;

import java.util.*;

public class MethodParser {


    public static void main(String[] args) {

    }

    /**
     * Parses string that represents a method signature and stores all it's members into a {@link MethodSignature} object.
     * signatureString is a java-like method signature with following parts:
     * 1. access modifier - optional, followed by space: ' '
     * 2. return type - followed by space: ' '
     * 3. method name
     * 4. arguments - surrounded with braces: '()' and separated by commas: ','
     * Each argument consists of argument type and argument name, separated by space: ' '.
     * Examples:
     * accessModifier returnType methodName(argumentType1 argumentName1, argumentType2 argumentName2)
     * private void log(String value)
     * Vector3 distort(int x, int y, int z, float magnitude)
     * public DateTime getCurrentDateTime()
     *
     * @param signatureString source string to parse
     * @return {@link MethodSignature} object filled with parsed values from source string
     */
    public MethodSignature parseFunction(String signatureString) {
        boolean hasAccessModifier = hasAccessModifier(signatureString);
        String accessModifier = hasAccessModifier ? getAccessModifier(signatureString) : null;
        String returnType = getReturnType(signatureString, hasAccessModifier);
        String methodName = getMethodName(signatureString, hasAccessModifier);
        List<MethodSignature.Argument> methodArguments = getMethodArguments(signatureString);
        MethodSignature methodSignature = new MethodSignature(methodName, methodArguments);
        methodSignature.setReturnType(returnType);
        methodSignature.setAccessModifier(accessModifier);
        return methodSignature;

    }

    private String getAccessModifier(String signatureString) {
        return signatureString.substring(0, signatureString.indexOf(' '));
    }

    public boolean hasAccessModifier(String signatureString) {
        return signatureString.contains("public") ||
                signatureString.contains("private") ||
                signatureString.contains("protected");
    }

    public String getReturnType(String signatureString, boolean hasAccessModifier) {
        int startingIndex = hasAccessModifier ? signatureString.indexOf(' ') + 1 : 0;
        int endIndex = hasAccessModifier ? signatureString.indexOf(' ', startingIndex + 1) : signatureString.indexOf(' ');
        return signatureString.substring(startingIndex, endIndex);
    }

    public String getMethodName(String signatureString, boolean hasAccessModifier) {
        int indexOfFirstSpace = signatureString.indexOf(' ');
        int startingIndex = hasAccessModifier ? signatureString.indexOf(' ', indexOfFirstSpace + 1) + 1 : indexOfFirstSpace + 1;
        int endIndex = signatureString.indexOf('(');
        return signatureString.substring(startingIndex, endIndex);
    }

    public List<MethodSignature.Argument> getMethodArguments(String signatureSting) {
       String argumentsString = signatureSting.substring(signatureSting.indexOf('(') + 1, signatureSting.indexOf(')'));
       String[] arguments = argumentsString.split(", ");
       List<MethodSignature.Argument> argumentsList = new ArrayList<>();
       for (String argument : arguments) {
           if (!argument.isEmpty()) {
               String[] type_name = argument.split(" ");
               argumentsList.add(new MethodSignature.Argument(type_name[0], type_name[1]));
           }
       }
       return argumentsList;
   }


}
