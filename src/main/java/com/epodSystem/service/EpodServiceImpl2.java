package com.epodSystem.service;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.epodSystem.model.EpodDtls;
import com.epodSystem.model.Response;
import com.epodSystem.repository.EpodRepository;

@Service
@Qualifier("epodServiceImpl2")
public class EpodServiceImpl2 implements EpodService{

	Response response;
	
	@Autowired
	EpodRepository repository;
	
	@Autowired
	UploadDocServiceImpl uploadDocService;
	
	@Override
	public Response saveEpod(EpodDtls epodDtls) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response saveBulkEpod(List<EpodDtls> epodDtls) {
		response = new Response();
		try {
		if(null!=epodDtls) {
		repository.save(epodDtls);
		response.setCode("0");
		response.setMsg("ePOD created");
		}
		else {
			response.setCode("1");
			response.setMsg("Unable to create ePOD");
		}
		}
		catch (Exception e) {
			response.setCode("1");
			response.setMsg("Unable to create ePOD");
			response.setAdditionalMsg(e.getMessage());
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public EpodDtls updateEpod(EpodDtls epodDtls) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EpodDtls getEpodById(int epodId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EpodDtls> epodList() {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean updateEpodForDoc(MultipartFile[] files, String remarks, int epodId) {
		boolean updated = false;
		String filePath = "";
		JSONObject loadedJson = new JSONObject();
		EpodDtls epod = new EpodDtls();
		JSONArray jArray = new JSONArray();
		if (null != epod && null != files && files.length > 0) {
			for (MultipartFile file : files) {
				filePath = uploadDocService.storeFile(file);
				if (null != filePath && !filePath.equals("")) {
					JSONObject custDoc = new JSONObject();
					custDoc.put("docName", file.getOriginalFilename());
					custDoc.put("docPath", filePath);
					jArray.add(custDoc);
				}
			}
			
			if (null != jArray && jArray.size() > 0) {
				loadedJson.put("DOC", jArray);
				repository.setLoadedDocForEpodDtls(loadedJson, epodId);
				updated = true;
			}
		}
		return updated;
	}

	@Override
	public boolean updateEpodDtls(EpodDtls epodDtls) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Boolean updateEpodStatusForACK(int epodId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> findVehicleByApiKey(String apiKey) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
