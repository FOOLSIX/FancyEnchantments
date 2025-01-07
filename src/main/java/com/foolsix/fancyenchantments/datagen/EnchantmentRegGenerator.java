package com.foolsix.fancyenchantments.datagen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.squareup.javapoet.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.lang.model.element.Modifier;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;
import static javax.lang.model.element.Modifier.*;

public class EnchantmentRegGenerator {

    public static String toConstantName(String className) {
        StringBuilder constantName = new StringBuilder();
        for (int i = 0; i < className.length(); i++) {
            char c = className.charAt(i);
            if (Character.isUpperCase(c) && i > 0) {
                constantName.append('_');
            }
            constantName.append(Character.toUpperCase(c));
        }
        return constantName.toString();
    }

    private static void generateRegistration() {
        TypeSpec.Builder typeSpecBuilder = TypeSpec.classBuilder("EnchantmentReg")
                .addModifiers(PUBLIC, FINAL);


        typeSpecBuilder.addField(FieldSpec.builder(ParameterizedTypeName.get(DeferredRegister.class, Enchantment.class), "ENCHANTMENTS")
                .addModifiers(PUBLIC, STATIC, FINAL)
                .initializer(
                        "DeferredRegister.create($T.ENCHANTMENTS, $S)",
                        ForgeRegistries.class, MODID
                )
                .build());

        typeSpecBuilder.addMethod(MethodSpec.methodBuilder("reg")
                .addModifiers(PUBLIC, STATIC)
                .returns(ParameterizedTypeName.get(RegistryObject.class, Enchantment.class))
                .addParameter(String.class, "name")
                .addParameter(ParameterizedTypeName.get(ClassName.get(Supplier.class), WildcardTypeName.subtypeOf(Enchantment.class)), "sup")
                .addStatement("return ENCHANTMENTS.register(name, sup)")
                .build());

        typeSpecBuilder.addMethod(MethodSpec.methodBuilder("register")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(void.class)
                .addParameter(IEventBus.class, "eventBus")
                .addStatement("ENCHANTMENTS.register(eventBus)")
                .build());

        try {
            Files.newDirectoryStream(Paths.get("src/main/java/com/foolsix/fancyenchantments/enchantment")).forEach(path -> {
                        if (Files.isRegularFile(path) && path.toString().endsWith(".java")) {
                            String enchantClassName = path.getFileName().toString().replace(".java", "");
                            String constName = toConstantName(enchantClassName);
                            typeSpecBuilder.addField(FieldSpec.builder(ParameterizedTypeName.get(RegistryObject.class, Enchantment.class), constName)
                                    .addModifiers(PUBLIC, STATIC, FINAL)
                                    .initializer(
                                            "reg($S, $T::new)",
                                            constName.toLowerCase(), ClassName.get("com.foolsix.fancyenchantments.enchantment", enchantClassName)
                                    )
                                    .build());
                            generateLang(constName.toLowerCase(), "en_us.json");
                            generateLang(constName.toLowerCase(), "zh_cn.json");
                        }
                    });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        JavaFile javaFile = JavaFile.builder("com.foolsix.fancyenchantments.enchantment.util", typeSpecBuilder.build())
                .build();

        try {
            javaFile.writeTo(new File("src\\main\\java"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void generateLang(String name, String fileName) {
        String filePath = "src/main/resources/assets/fancyenchantments/lang/" + fileName;
        File file = new File(filePath);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        JsonObject rootObject;
        try (Reader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)) {
            rootObject = gson.fromJson(reader, JsonObject.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String checkKey = String.format("enchantment.fancyenchantments.%s", name);
        String descKey = String.format("enchantment.fancyenchantments.%s.desc", name);

        Set<String> keysSet = new HashSet<>();
        for (Map.Entry<String, JsonElement> entry : rootObject.entrySet()) {
            keysSet.add(entry.getKey());
        }

        if (!keysSet.contains(checkKey)) {
            rootObject.addProperty(checkKey, "");
            try (Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)) {
                gson.toJson(rootObject, writer);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("add " + checkKey + " in " + fileName);
        }

        if (!keysSet.contains(descKey)) {
            rootObject.addProperty(descKey, "");
            try (Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)) {
                gson.toJson(rootObject, writer);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("add " + descKey + " in " + fileName);
        }
    }


    public static void main(String[] args) {
        generateRegistration();
    }
}

