package com.javaCrud;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Date;
import java.text.ParseException;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;

import com.google.cloud.firestore.Firestore;

public class Interface {
    private static String ultimaData = "";
    private static int porcentagemMaisRecente = 0;
    

    public static void startInterface(Firestore db) {
        DefaultTableModel tableModelMaisInfo = new DefaultTableModel();

        Map<String, Object> firstRow = FirestoreOperations.readFirstRow(db, "plantacao", tableModelMaisInfo);

        ultimaData = (String) firstRow.get("datetime");
        Long porcentagemLong = (Long) firstRow.get("umidade_solo");
        porcentagemMaisRecente = (porcentagemLong != null) ? porcentagemLong.intValue() : 0;
        
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            Date date = dateFormat.parse(ultimaData);
        
            SimpleDateFormat newDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            ultimaData = newDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        JFrame miJFrame = new JFrame("Interface");
        miJFrame.setSize(1340, 750);

        JPanel miJPanel = new JPanel();
        miJPanel.setSize(300, 300);

        miJPanel.setLayout(new GridBagLayout());

        URL iconURL = Interface.class.getResource("transferir2.png");
        ImageIcon icon = new ImageIcon(iconURL);

        Image imagemAjustada = icon.getImage().getScaledInstance(450, 450, Image.SCALE_SMOOTH);
        ImageIcon iconAjustado = new ImageIcon(imagemAjustada);

        JLabel miJLabelImagem = new JLabel(iconAjustado);

        JLabel miJLabelTexto = new JLabel("Porcentagem:");
        miJLabelTexto.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 40));

        miJLabelTexto.setText("Porcentagem: " + porcentagemMaisRecente + "%");

        JLabel miJLabelTexto2 = new JLabel("Molhe em:");
        miJLabelTexto2.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 40));
                
        int molheEm;
        if (porcentagemMaisRecente > 90) {
            molheEm = 5;
        } else if (porcentagemMaisRecente >= 60 && porcentagemMaisRecente <= 90) {
            molheEm = 4;
        } else if (porcentagemMaisRecente >= 40 && porcentagemMaisRecente < 60) {
            molheEm = 3;
        } else if (porcentagemMaisRecente >= 30 && porcentagemMaisRecente < 40) {
            molheEm = 2;
        } else if (porcentagemMaisRecente >= 20 && porcentagemMaisRecente < 30) {
            molheEm = 1;
        } else {
            molheEm = 0;
        }
        miJLabelTexto2.setText("Molhe em: " + molheEm + " dias.");

        JButton miJButton = new JButton("Mais Informações");

        miJButton.setOpaque(false);
        miJButton.setContentAreaFilled(false);
        miJButton.setBorderPainted(true);
        miJButton.setFocusPainted(false);
        miJButton.setBorder(new RoundedBorder(15));

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 0, 150);
        miJPanel.add(miJLabelImagem, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(0, 0, 0, 200);
        miJPanel.add(miJLabelTexto, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        miJPanel.add(miJLabelTexto2, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(50, 0, 50, 200);
        miJPanel.add(miJButton, gbc);

        miJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame novaJanela = new JFrame("Mais Informações");
                novaJanela.setSize(1340, 750);
        
                JLabel outraImagemLabel = new JLabel();
                outraImagemLabel.setIcon(new ImageIcon("transferir2.png"));
        
                tableModelMaisInfo.addColumn("Data e Hora");
                tableModelMaisInfo.addColumn("Umidade Ar");
                tableModelMaisInfo.addColumn("Umidade Solo");
                tableModelMaisInfo.addColumn("Temperatura");
        
                JTable tabelaMaisInfo = new JTable(tableModelMaisInfo);

                FirestoreOperations.readCollection(db, "plantacao", tableModelMaisInfo);
        
                JScrollPane tabelaScrollPaneMaisInfo = new JScrollPane(tabelaMaisInfo);
                tabelaScrollPaneMaisInfo.setPreferredSize(new Dimension(500, 200));
        
                JPanel painelMaisInfo = new JPanel();
        
                painelMaisInfo.setLayout(new GridBagLayout());
                GridBagConstraints gbcMaisInfo = new GridBagConstraints();
        
                JLabel labelPorcentagem = new JLabel("Porcentagem:");
                labelPorcentagem.setFont(new java.awt.Font("arial", java.awt.Font.PLAIN, 30));
                labelPorcentagem.setText("Porcentagem: " + porcentagemMaisRecente + "%");
                gbcMaisInfo.gridx = 5;
                gbcMaisInfo.gridy = 0;
                painelMaisInfo.add(labelPorcentagem, gbcMaisInfo);
        
                JLabel labelUltimaRega = new JLabel("Ultima Rega:");
                labelUltimaRega.setFont(new java.awt.Font("arial", java.awt.Font.PLAIN, 30));
                labelUltimaRega.setText("Ultima Rega: " + ultimaData);
                gbcMaisInfo.gridx = 5;
                gbcMaisInfo.gridy = 3;
                painelMaisInfo.add(labelUltimaRega, gbcMaisInfo);
        
                gbcMaisInfo.gridx = 0;
                gbcMaisInfo.gridy = 1;
                painelMaisInfo.add(outraImagemLabel, gbcMaisInfo);

                JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
                splitPane.setDividerLocation(500);

                JPanel tableAndLabelsPanel = new JPanel(new BorderLayout());
                tableAndLabelsPanel.add(tabelaScrollPaneMaisInfo, BorderLayout.CENTER);
        
                JPanel labelsPanel = new JPanel();
                labelsPanel.setLayout(new GridLayout(1, 2));
                labelsPanel.add(labelPorcentagem);
                labelsPanel.add(labelUltimaRega);
        
                tableAndLabelsPanel.add(labelsPanel, BorderLayout.SOUTH);
                labelsPanel.setBorder(BorderFactory.createEmptyBorder(30, 10, 30, 10));
        
                splitPane.setLeftComponent(painelMaisInfo);
                splitPane.setRightComponent(tableAndLabelsPanel);
        
                novaJanela.add(splitPane);
                novaJanela.setVisible(true);
            }
        });

        miJFrame.add(miJPanel);

        miJFrame.setVisible(true);
        miJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

class RoundedBorder implements Border {
    private int radius;

    public RoundedBorder(int radius) {
        this.radius = radius;
    }

    public Insets getBorderInsets(Component c) {
        return new Insets(this.radius + 1, this.radius + 1, this.radius + 2, this.radius);
    }

    public boolean isBorderOpaque() {
        return true;
    }

    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
    }
}
