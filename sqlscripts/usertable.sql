CREATE TABLE "users" ( `id` INTEGER PRIMARY KEY AUTOINCREMENT, `username` TEXT NOT NULL, `password` TEXT NOT NULL, `valid` TEXT, `role` TEXT NOT NULL DEFAULT 'user', `associatedUser` TEXT, `nombre` TEXT, `apellidoPat` TEXT, `apellidoMat` TEXT )