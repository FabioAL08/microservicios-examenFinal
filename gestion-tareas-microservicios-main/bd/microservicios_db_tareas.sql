-- MySQL dump 10.13  Distrib 8.0.45, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: microservicios_db
-- ------------------------------------------------------
-- Server version	8.0.45

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `tareas`
--

DROP TABLE IF EXISTS `tareas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tareas` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `titulo` varchar(100) NOT NULL,
  `descripcion` text,
  `estado` varchar(20) DEFAULT 'PENDIENTE',
  `username` varchar(50) NOT NULL,
  `user_id` bigint NOT NULL,
  `fecha_creacion` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `fecha_actualizacion` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `fecha_vencimiento` date DEFAULT NULL,
  `prioridad` varchar(255) DEFAULT NULL,
  `porcentaje_completado` int DEFAULT '0',
  `etiquetas` varchar(255) DEFAULT NULL,
  `tiempo_estimado_horas` int DEFAULT NULL,
  `tiempo_real_horas` int DEFAULT NULL,
  `archivada` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_username` (`username`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_estado` (`estado`),
  KEY `idx_prioridad` (`prioridad`),
  KEY `idx_fecha_vencimiento` (`fecha_vencimiento`),
  KEY `idx_archivada` (`archivada`),
  KEY `idx_username_estado` (`username`,`estado`),
  CONSTRAINT `chk_tareas_estado` CHECK ((`estado` in (_utf8mb4'PENDIENTE',_utf8mb4'EN_PROGRESO',_utf8mb4'COMPLETADA',_utf8mb4'CANCELADA')))
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tareas`
--

LOCK TABLES `tareas` WRITE;
/*!40000 ALTER TABLE `tareas` DISABLE KEYS */;
INSERT INTO `tareas` VALUES (1,'Aprender Spring Boot','Completar el curso de microservicios','COMPLETADA','juanperez',2,'2026-04-15 14:21:11','2026-04-15 14:21:11','2026-05-15','ALTA',100,NULL,10,NULL,0),(2,'Implementar JWT','Agregar autenticación con tokens','EN_PROGRESO','juanperez',2,'2026-04-15 14:21:11','2026-04-15 14:21:11','2026-04-30','ALTA',60,NULL,8,NULL,0),(3,'Crear frontend','Desarrollar interfaz de usuario','PENDIENTE','juanperez',2,'2026-04-15 14:21:11','2026-04-15 14:21:11','2026-05-30','MEDIA',0,NULL,20,NULL,0),(4,'Documentar API','Escribir documentación con Swagger','PENDIENTE','maria',3,'2026-04-15 14:21:11','2026-04-15 14:21:11','2026-06-14','BAJA',10,NULL,5,NULL,0),(5,'Hacer pruebas','Probar todos los endpoints','EN_PROGRESO','admin',1,'2026-04-15 14:21:11','2026-04-15 14:21:11','2026-04-22','MEDIA',50,NULL,4,NULL,0),(6,'Optimizar consultas','Revisar y optimizar queries SQL','PENDIENTE','carlos',4,'2026-04-15 14:21:11','2026-04-15 14:21:11','2026-04-25','ALTA',0,NULL,6,NULL,0),(7,'Revisar seguridad','Auditoría de seguridad del sistema','CANCELADA','admin',1,'2026-04-15 14:21:11','2026-04-15 14:21:11','2026-04-10','ALTA',0,NULL,3,NULL,1),(8,'Desplegar a producción','Subir cambios al ambiente productivo','COMPLETADA','juanperez',2,'2026-04-15 14:21:11','2026-04-15 14:21:11','2026-04-13','ALTA',100,NULL,2,NULL,0),(9,'Actualizar documentación','Revisar y actualizar docs','PENDIENTE','maria',3,'2026-04-15 14:21:11','2026-04-15 14:21:11','2026-05-05','BAJA',0,NULL,3,NULL,0),(10,'Revisar PR','Hacer code review','EN_PROGRESO','admin',1,'2026-04-15 14:21:11','2026-04-15 14:21:11','2026-04-16','MEDIA',75,NULL,1,NULL,0),(12,'Documentar API','Escribir documentación con Swagger','EN_PROGRESO','reydi2huallparttupa',1,'2026-04-15 18:07:57','2026-04-15 19:43:28',NULL,'MEDIA',75,NULL,NULL,6,0),(13,'Tarea Actualizada','Nueva descripción','EN_PROGRESO','reydi2huallparttupa',1,'2026-04-15 18:26:22','2026-04-15 19:46:24','2025-01-15','MEDIA',25,NULL,10,NULL,0),(14,'Tarea V2','Tarea con prioridad alta','PENDIENTE','reydi2huallparttupa',1,'2026-04-15 19:31:42','2026-04-15 19:31:42','2024-12-31','ALTA',0,NULL,8,NULL,0);
/*!40000 ALTER TABLE `tareas` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-04-15 15:27:22
