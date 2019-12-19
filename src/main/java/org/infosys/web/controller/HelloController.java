package org.infosys.web.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.infosys.bo.DBGenerator;
import org.infosys.bo.EmailBO;
import org.infosys.bo.GenerateJavaCode;
import org.infosys.bo.ImageGenerator;
import org.infosys.bo.JsonParserBO;
import org.infosys.bo.JsonReadAndWriter;
import org.infosys.bo.OrmGenerator;
import org.infosys.bo.XMIParser;
import org.infosys.dao.BaseDAO;
import org.infosys.vo.common.DBProperties;
import org.infosys.vo.common.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
// @SessionAttributes("page")
public class HelloController {

	@Autowired
	private User user;

	public void setUser(User user) {
		this.user = user;
	}

	@Autowired
	EmailBO mailBO;

	public void setMailBO(EmailBO mailBO) {
		this.mailBO = mailBO;
	}

	@Autowired
	BaseDAO baseDAO;

	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView uml(ModelMap model) {

		return new ModelAndView("home");

	}

	@RequestMapping(value = "/erd", method = RequestMethod.GET)
	public ModelAndView erd(ModelMap model) {

		return new ModelAndView("erd");

	}

	@RequestMapping(value = "/uml", method = RequestMethod.GET)
	public ModelAndView home(ModelMap model) {
		return new ModelAndView("uml");

	}

	@RequestMapping(value = "/index/{page}", method = RequestMethod.GET)
	public ModelAndView index(@PathVariable("page") String page) {
		System.out.println("Page: " + page);
		user.setPage(page);
		return new ModelAndView("index", "user", user);

	}
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index() {
		return new ModelAndView("index");

	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@ModelAttribute("userForm") User user, HttpServletRequest request, Model model) {
		String returnPage = null;
		System.out.println("User:" + user);
		user = baseDAO.getUserDetails(user);
		if (user.getStatusCode() == 1) {
			HttpSession session = request.getSession();
			session.setMaxInactiveInterval(1000);
			session.setAttribute("user", user);
			if (null != user.getPage() && !(user.getPage().equalsIgnoreCase(""))) {
				returnPage = user.getPage();
			} else {
				returnPage = "home";
			}
		} else if (user.getStatusCode() == 2) {
			returnPage = "payment";
		} else {
			model.addAttribute("msg", user.getStatusMessage());
			returnPage = "errorPage";
		}
		return returnPage;
	}

	
	@RequestMapping(value = "/signUp", method = RequestMethod.POST)
	public String signUp(@ModelAttribute("userForm") User user, Model model, HttpServletRequest request) {
		String returnPage = null;
		System.out.println("User:" + user);
		String oneTimePassword = baseDAO.getRandomNumber();
		user.setOneTimePassword(oneTimePassword);
			if (mailBO.sendMail(user)) {
				HttpSession session = request.getSession();
				session.setMaxInactiveInterval(1000);
				session.setAttribute("userObj", user);
				returnPage = "oneTimePassword";
			} else {
				model.addAttribute("msg", "Mail Server Issue");
				returnPage = "errorPage";
			}
		return returnPage;
	}

	@RequestMapping(value = "/logOut", method = RequestMethod.GET)
	public String logOut(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute("user");
		return "home";
	}

	@RequestMapping(value = "/changePassword", method = RequestMethod.GET)
	public ModelAndView changePassword(ModelMap model) {
		return new ModelAndView("changePassword");
	}

	@RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
	public String changePassword(@ModelAttribute("userForm") User user, Model model, HttpServletRequest request) {
		String returnPage = null;
		user.setEmail(user.getRecoveryMail());
		user = baseDAO.updatePassword(user);
		if (user.getStatusCode() == 1) {
			model.addAttribute("msg", "Password updated successfully..");
			returnPage = "success";
		} else {
			model.addAttribute("msg", user.getStatusMessage());
			returnPage = "errorPage";
		}
		return returnPage;
	}

	@RequestMapping(value = "/newPassword", method = RequestMethod.GET)
	public ModelAndView newPassword(ModelMap model) {
		return new ModelAndView("newPassword");
	}

	@RequestMapping(value = "/savePassword", method = RequestMethod.POST)
	public String savePassword(@ModelAttribute("userForm") User user, Model model, HttpServletRequest request) {
		String returnPage = null;
		user.setEmail(user.getRecoveryMail());
		baseDAO.savePassword(user);
		if (user.getStatusCode() == 1) {
			model.addAttribute("msg", "Password saved successfully..");
			returnPage = "success";
		} else {
			model.addAttribute("msg", user.getStatusMessage());
			returnPage = "errorPage";
		}
		return returnPage;
	}

	@RequestMapping(value = "/oneTimePassword", method = RequestMethod.GET)
	public ModelAndView oneTimePassword(ModelMap model) {
		return new ModelAndView("oneTimePassword");
	}

	@RequestMapping(value = "/verifyPassword", method = RequestMethod.POST)
	public String verifyPassword(@ModelAttribute("userForm") User user, HttpServletRequest request, Model model) {
		String returnPage = null;
		System.out.println("User:" + user);
		HttpSession session = request.getSession();
		user = isValidPassword(user, session);
		if (user.getStatusCode() == 1) {
			session.setMaxInactiveInterval(1000);
			session.setAttribute("user", user);
			if (null != user.getPage() && !(user.getPage().equalsIgnoreCase(""))) {
				returnPage = user.getPage();
			} else {
				returnPage = "home";
			}
		} else {
			model.addAttribute("msg", user.getStatusMessage());
			returnPage = "errorPage";
		}
		return returnPage;
	}

	
	private User isValidPassword(User user, HttpSession session) {
		String userPassword = user.getOneTimePassword();
		String systemPassword = null;
		if (null != session.getAttribute("userObj")) {
			user = (User) session.getAttribute("userObj");
			systemPassword = user.getOneTimePassword();
			if (null != systemPassword && null != userPassword) {
				if (systemPassword.equalsIgnoreCase(userPassword)) {
					user = baseDAO.saveUser(user);
				}
				else
				{
					user.setStatusMessage("One Time Password is Incorrect!");
				}
			}
		} /*else {
			System.out.println(" Session User:" + user);
			user = baseDAO.verifyOneTimePassword(user);
		}*/
		return user;
	}
	
	@RequestMapping(value = "/work", method = RequestMethod.GET)
	public ModelAndView work(ModelMap model) {
		return new ModelAndView("folderDialog");
	}

	@RequestMapping(value = "/gDrive", method = RequestMethod.GET)
	public ModelAndView gDrive(ModelMap model) {

		return new ModelAndView("gDrive");
	}

	@RequestMapping(value = "/hello/{name}", method = RequestMethod.GET)
	public ModelAndView hello(@PathVariable("name") String name) {

		ModelAndView model = new ModelAndView();
		model.setViewName("hello");
		model.addObject("msg", name);

		return model;

	}
	
	@RequestMapping(value = "/saveToGDrive", method = { RequestMethod.POST })
	public @ResponseBody String saveToGDrive(@RequestParam String fileName, @RequestBody String data) {
		System.out.println("Data:" + data);
		return data;
	}

	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	public @ResponseBody String save(@RequestParam String fileName, @RequestBody String data) {
		System.out.println("Data:" + data);
		JsonReadAndWriter readAndWriter = new JsonReadAndWriter();
		readAndWriter.write(fileName, data);
		System.out.println("fileName:" + fileName);
		System.out.println("Data :" + data);
		return "Saved Successfully";
	}

	@RequestMapping(value = "/saveImage", method = { RequestMethod.POST })
	public @ResponseBody String saveImage(@RequestParam String fileName, @RequestParam String fileType,
			@RequestBody String data) {
		System.out.println("fileName:" + fileName + " fileType:" + fileType + " Data:" + data);
		String statusMessage = null;
		if (null != fileType && !fileType.equalsIgnoreCase("")) {
			JsonReadAndWriter readAndWriter = new JsonReadAndWriter();
			readAndWriter.writeToFile(fileName + ".svg", data);
			if (!fileType.equalsIgnoreCase("SVG")) {
				ImageGenerator imageGenerator = new ImageGenerator();
				imageGenerator.generateOutput(fileType, data, fileName);
			}
			statusMessage = "Saved Successfully";
		} else {
			statusMessage = "File Type is Null";
		}
		return statusMessage;
	}

	@RequestMapping(value = "/saveWorkspace", method = { RequestMethod.POST })
	public ModelAndView saveWorkspace(@RequestBody String data) {
		String success = null;
		System.out.println("Data:" + data);
		String[] temp;
		String delimiter = "#";
		temp = data.split(delimiter);
		System.out.println("Name: " + temp[0] + " Password: " + temp[1] + " Mobile: " + temp[2] + " Email: " + temp[3]
				+ " Workspace:" + temp[4]);
		User user = new User();
		user.setName(temp[0].trim());
		user.setPassword(temp[1].trim());
		user.setMobile(temp[2].trim());
		user.setEmail(temp[3].trim());
		user.setWorkspace(temp[4].trim());
		/*
		 * if (0 < baseDAO.saveUser(user)) success = "Saved Successfully"; else
		 * success = "User Existing";
		 */
		System.out.println(success);
		return new ModelAndView("home");

	}

	@RequestMapping(value = "/exportXmi", method = { RequestMethod.POST })
	public @ResponseBody String exportXmi(@RequestParam String fileName, @RequestBody String data) {
		XMIParser xmiParser = new XMIParser();
		xmiParser.createXMI(fileName, data);
		System.out.println("fileName:" + fileName);
		System.out.println("Data :" + data);
		return "Saved Successfully";
	}

	@RequestMapping(value = "/importXmi", method = RequestMethod.GET)
	public @ResponseBody String importXmi(@RequestParam String fileName) {

		System.out.println("fileName:" + fileName);
		JsonParserBO parser = new JsonParserBO();
		String response = parser.readXmi(fileName);
		System.out.println("Response: " + response);
		return response;
	}

	@RequestMapping(value = "/generateJava", method = { RequestMethod.POST })
	public @ResponseBody String generateJava(@RequestParam String fileName, @RequestBody String data) {
		GenerateJavaCode javaCode = new GenerateJavaCode();
		javaCode.generate(data, fileName);
		System.out.println("fileName:" + fileName);
		System.out.println("Data :" + data);
		return "Saved Successfully";
	}

	@RequestMapping(value = "/read", method = RequestMethod.GET)
	public @ResponseBody String read(@RequestParam String data) {

		System.out.println("fileName:" + data);
		JsonReadAndWriter readAndWriter = new JsonReadAndWriter();
		String response = readAndWriter.read(data);
		System.out.println("Response: " + response);
		return response;
	}

	
	@RequestMapping(value = "/readFile", method = RequestMethod.GET)
	public @ResponseBody String readFile(@RequestBody File data) {

		System.out.println("fileName:" + data);
		JsonReadAndWriter readAndWriter = new JsonReadAndWriter();
		String response = readAndWriter.readObject(data);
		System.out.println("Response: " + response);
		return response;
	}
	
	@RequestMapping(value = "/reverse", method = RequestMethod.GET)
	public @ResponseBody String reverse(@RequestParam String fileName) {

		System.out.println("fileName:" + fileName);
		JsonParserBO parser = new JsonParserBO();
		String response = parser.javaToJsonParser(fileName);
		System.out.println("Response: " + response);
		return response;
	}

	@RequestMapping(value = "/generateDB", method = { RequestMethod.POST })
	public @ResponseBody String generateDB(@RequestBody String data) {
		String[] temp;
		String[] temp1;
		String[] temp2;
		String[] temp3;
		String delimiter = "&";
		String delimiter1 = "=";
		temp = data.split(delimiter);
		temp1 = temp[1].split(delimiter1);
		temp2 = temp[2].split(delimiter1);
		temp3 = temp[3].split(delimiter1);
		DBProperties dbProperties = new DBProperties();
		dbProperties.setURL(temp1[1]);
		dbProperties.setUserName(temp2[1]);
		dbProperties.setPassword(temp3[1]);
		System.out.println("Data :" + temp[0]);
		System.out.println("dbUrl:" + temp1[1]);
		System.out.println("userName:" + temp2[1]);
		System.out.println("pwd:" + temp3[1]);
		DBGenerator dbGenerator = new DBGenerator();
		dbGenerator.generateDB(dbProperties, temp[0]);
		return dbProperties.getStatusMessage();
	}
	
	@RequestMapping(value = "/reverseDB", method = RequestMethod.POST)
	public @ResponseBody String reverseDB(@RequestBody String data) {
		System.out.println(data);
		String[] temp;
		String[] temp1;
		String[] temp2;
		String[] temp3;
		String delimiter = "&";
		String delimiter1 = "=";
		temp = data.split(delimiter);
		temp1 = temp[0].split(delimiter1);
		temp2 = temp[1].split(delimiter1);
		temp3 = temp[2].split(delimiter1);
		DBProperties dbProperties = new DBProperties();
		dbProperties.setURL(temp1[1]);
		dbProperties.setUserName(temp2[1]);
		dbProperties.setPassword(temp3[1]);		
		JsonParserBO parser = new JsonParserBO();
		String response = parser.dbJsonParser(dbProperties);
		System.out.println("Response: " + response);
		return response;
	}
	
	@RequestMapping(value = "/generateORM", method = { RequestMethod.POST })
	public @ResponseBody String generateORM(@RequestBody String data) {
		Map<Integer, String> dbPropMap = new HashMap<Integer, String>();
		String[] temp;
		String[] temps;
		String delimiter = "&";
		String delimiter1 = "=";
		temp = data.split(delimiter);
		DBProperties dbProperties = new DBProperties();
		System.out.println("length: "+temp.length);
		for(int i=1; i<temp.length; i++)
		{
			temps = temp[i].split(delimiter1);
			System.out.println("props: "+temps[1]);
			dbPropMap.put(i, temps[1]);
		}
		dbProperties.setURL(dbPropMap.get(1));
		dbProperties.setUserName(dbPropMap.get(2));
		dbProperties.setPassword(dbPropMap.get(3));
		dbProperties.setProjectPath(dbPropMap.get(4));
		dbProperties.setProjectName(dbPropMap.get(5));
		dbProperties.setPackageName(dbPropMap.get(6));
		dbProperties.setDB(dbPropMap.get(7));
		System.out.println("Data :" + temp[0]);
		System.out.println("dbProperties:" + dbProperties.toString());
		OrmGenerator ormGenerator = new OrmGenerator();
		ormGenerator.generateORMHibernateCode(dbProperties,temp[0]);
		return dbProperties.getStatusMessage();
	}

}