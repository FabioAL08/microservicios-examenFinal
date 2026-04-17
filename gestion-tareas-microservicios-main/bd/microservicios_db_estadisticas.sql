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
-- Table structure for table `estadisticas`
--

DROP TABLE IF EXISTS `estadisticas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `estadisticas` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `total_tareas` int DEFAULT '0',
  `tareas_completadas` int DEFAULT '0',
  `tareas_pendientes` int DEFAULT '0',
  `tareas_progreso` int DEFAULT '0',
  `tareas_canceladas` int DEFAULT '0',
  `tareas_prioridad_alta` int DEFAULT '0',
  `tareas_prioridad_media` int DEFAULT '0',
  `tareas_prioridad_baja` int DEFAULT '0',
  `tareas_atrasadas` int DEFAULT '0',
  `tareas_archivadas` int DEFAULT '0',
  `promedio_porcentaje_completado` double DEFAULT '0',
  `promedio_tiempo_estimado` double DEFAULT '0',
  `promedio_tiempo_real` double DEFAULT '0',
  `eficiencia_tiempo` double DEFAULT '0',
  `fecha_actualizacion` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  KEY `idx_estadisticas_username` (`username`),
  KEY `idx_fecha_actualizacion` (`fecha_actualizacion`),
  CONSTRAINT `chk_estadisticas_totales` CHECK (((`total_tareas` >= 0) and (`tareas_completadas` >= 0) and (`tareas_pendientes` >= 0) and (`tareas_progreso` >= 0) and (`tareas_canceladas` >= 0)))
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estadisticas`
--

LOCK TABLES `estadisticas` WRITE;
/*!40000 ALTER TABLE `estadisticas` DISABLE KEYS */;
INSERT INTO `estadisticas` VALUES (1,'admin',3,0,0,2,0,0,2,0,0,1,62.5,2.5,NULL,0,'2026-04-15 14:21:11'),(2,'carlos',1,0,1,0,0,1,0,0,0,0,0,6,NULL,0,'2026-04-15 14:21:11'),(3,'juanperez',4,2,1,1,0,3,1,0,0,0,65,10,NULL,0,'2026-04-15 14:21:11'),(4,'maria',2,0,2,0,0,0,0,2,0,0,5,4,NULL,0,'2026-04-15 14:21:11'),(8,'reydi2huallparttupa',3,0,1,2,0,1,2,0,0,0,0,0,0,0,'2026-04-15 19:46:23');
/*!40000 ALTER TABLE `estadisticas` ENABLE KEYS */;
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
