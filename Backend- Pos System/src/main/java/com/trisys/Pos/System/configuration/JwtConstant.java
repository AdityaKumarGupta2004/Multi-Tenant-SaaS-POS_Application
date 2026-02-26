package com.trisys.Pos.System.configuration;

import io.github.cdimascio.dotenv.Dotenv;

public class JwtConstant {

    // Load .env file from project root
    private static final Dotenv dotenv =
            Dotenv.configure()
                    .directory("./")
                    .ignoreIfMissing()
                    .load();

    public static final String JWT_SECRET =
            dotenv.get("JWT_SECRET_KEY");

    public static final String JWT_HEADER = "Authorization";
}