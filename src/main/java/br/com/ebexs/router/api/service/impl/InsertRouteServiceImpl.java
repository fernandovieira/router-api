package br.com.ebexs.router.api.service.impl;

import br.com.ebexs.router.api.service.InsertRouteService;
import br.com.ebexs.routerfindermodel.model.RouteModel;
import br.com.ebexs.routerfinderservice.service.ReadRouteRegistry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Service
public class InsertRouteServiceImpl implements InsertRouteService {

    private ReadRouteRegistry readRouteRegistry;
    private Environment environment;
    private String fileName;

    @Autowired
    public InsertRouteServiceImpl(Environment environment, ReadRouteRegistry readRouteRegistry) {
        this.environment = environment;
        this.readRouteRegistry = readRouteRegistry;
    }

    @Override
    public ResponseEntity insertRoute(RouteModel routeModel) throws IOException {

        fileName = environment.getProperty("file.path");

        List<RouteModel> routeModels = readRouteRegistry.read(fileName);


        Long exist = routeModels.stream()
                   .filter(model -> model.equals(routeModel))
                   .count();

        if (exist > 0) return ResponseEntity.badRequest().body("Route already exist on file");

        routeModels.add(routeModel);

        try {
            writeFile(routeModels);
            return ResponseEntity.status(201).body("");
        } catch (IOException exception) {
            return ResponseEntity.status(500).body(exception.getMessage());
        }


    }

    private void writeFile(List<RouteModel> routeModels) throws IOException {

        StringBuilder content = new StringBuilder();
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        routeModels.stream()
                .forEach( routeModel -> {
                    content.append(routeModel.toString()).append("\n");
                });

        writer.write(content.toString());

        writer.close();
    }
}
