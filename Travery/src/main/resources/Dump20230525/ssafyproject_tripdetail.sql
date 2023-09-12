-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: ssafyproject
-- ------------------------------------------------------
-- Server version	8.0.32

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
-- Table structure for table `tripdetail`
--

DROP TABLE IF EXISTS `tripdetail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tripdetail` (
  `article_no` int NOT NULL AUTO_INCREMENT,
  `parent_no` int NOT NULL,
  `content_id` int NOT NULL,
  `title` varchar(200) DEFAULT NULL,
  `addr1` varchar(300) DEFAULT NULL,
  `overview` varchar(10000) DEFAULT NULL,
  `content` varchar(2000) DEFAULT NULL,
  `start_date` varchar(50) DEFAULT NULL,
  `end_date` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`article_no`),
  KEY `fk_tripdetail_tripplanBoard` (`parent_no`),
  CONSTRAINT `fk_tripdetail_tripplanBoard` FOREIGN KEY (`parent_no`) REFERENCES `tripplanboard` (`article_no`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tripdetail`
--

LOCK TABLES `tripdetail` WRITE;
/*!40000 ALTER TABLE `tripdetail` DISABLE KEYS */;
INSERT INTO `tripdetail` VALUES (1,1,126334,'약사암(광주)','광주광역시 동구 증심사길160번길 89','약사암은 증심사의 부속 암자로 새인봉 아래쪽에 자리잡고 있다. 신라시대에 도윤(道允)이 847년(문성왕 9) 당나라에서 귀국한 뒤 창건하였다고 전하는데 당시에는 인왕사(人王寺)라고 했다가, 고려 예종(1105~1122) 때 국사 혜조(慧照)가 중창하면서 절 이름을 약사암으로 바꾸었다.조선시대인 1856년(철종 7) 3월에 성암(性庵)이 약사전을 중수하였고, 1905년 9월에 선주(善周)가 관청의 도움과 시주로 중건했다. 이후 1970년대부터 주지 석담(石潭)이 법당과 요사채 등을 중건·중수하여 오늘에 이르고 있다. * 약사사의 볼거리와 문화재 * 약사암에 보존되어 있는 유물로 석조여래좌상(보물)과 3층석탑이 있다. 이 두 가지의 유물이 9세기 전반의 작품으로 추정되어 이 절의 역사를 뒷받침해 주고 있다. 현재 약사암에는 대웅전, 운림당, 운림서원, 강원, 일주문 등이 있으나, 모두 1970년 대 후반부터 1980년대 중반의 건물이다. 대웅전에는 독성탱화·칠성탱화·산신탱화·신중탱화 등의 불화가 있는데 독성탱화는 1912년에 그린 것이며 칠성탱화와 산신탱화도 이와 비슷한 시기의 작품으로 추정된다. 신중탱화는 근래의 것이다. [문화재정보] 지정종목 : 문화재자료 지정번호 : 광주문화재자료 지정연도 : 1984년 2월 17일 시대 : 신라 종류 : 사찰','약사암!!12313',NULL,NULL),(2,1,130811,'광주광역시립산수도서관','광주광역시 동구 경양로 355','1997년 개관한 광주광역시립 산수도서관은 현재 광주시립도서관으로 통합된 도서관이다. 산수도서관은 급변하는 정보화 시대에 이용자의 정보욕구를 충족하고 쾌적한 독서환경을 위하여 정숙한 도서관환경을 만들어 나가면서 필요한 모든 자료를 고루 갖추고 이용자에 대한 서비스 질을 높여서 시민들이 필요한 적정 정보를 쉽고, 빠르고, 편리하게 접할 수 있는 도서관으로 성장할 수 있도록 하고자 함에 목표를 두고 있다. 광주광역시의 도서관은 1981년 무등도서관이 최초로 개관한 이래, 1989년 사직도서관이, 1997년 산수도서관이 각각 운영되었다. 이후 1998년 4월에 3개의 도서관을 통합하여 시립도서관으로 명칭을 개칭하게 되었다.시립 도서관에서는 현재 55만 3천여 권의 도서와 2만 2천여 점의 비도서, 그리고 1만여 점의 전자자료를 소장하고 있다.','123123',NULL,NULL),(3,1,130639,'우제길미술관','광주광역시 동구 의재로 140-6','* 국내 추상 거장, 우제길미술관 * 1995년 광주비엔날레 인기작가상과 문화체육부 공로상, 옥관 문화훈장을 받은 화가 우제길이 광주 지역의 예술문화 발전에 기여하고자 설립한 사립미술관이다. 광주는 전통적 예향도시이자 광주비엔날레를 비롯한 활발한 문화행사 등이 끊이지 않는 뜻깊은 곳으로 우제길미술관은 지역사회에 다양한 현대미술의 이해와 그 활성화에 기여하는 것을 목적으로 설립되었다. 또한 직접적인 작가의 삶과 작업과정, 다양하고 풍부한 자료 등을 체험할 수 있는 공간으로서의 역할을 수행하고 작가 우제길의 작품세계를 다양하고 진진하게 체험할 수 있는 공간이다. 지상 2층, 지하 2층 규모로 지하층에는 전시장과 수장고, 아트숍 등이 있고, 지상 1층은 전시장으로 이루어져 있다. 1955년부터 현재까지의 우제길 작품들과 호남지방 추상회화의 주요 작품들도 소장하고 있다 * 빛의 화가 우제길에 대하여 * 우제길은 \'빛의 화가\'라고 불리는 작가로 현대 미술의 거장이다. 1942년 일본 교토에서 태어나 광주서중학교를 거쳐 1961년 광주사범학교를 졸업하였다. 이후 1987년 광주대학교 산업디자인과를 졸업하였다. 한국과 일본, 미국, 유라시아 국가에서 180여 회 이상의 국제전과 기획전에 참여하였으며, 한국미술대상전 특별상(1976), 문화체육부 공로상(1994), 제1회 광주비엔날레 인기작가상(1995), 옥관 문화훈장(2004) 등을 받았다.','234',NULL,NULL);
/*!40000 ALTER TABLE `tripdetail` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-05-25 15:46:25
