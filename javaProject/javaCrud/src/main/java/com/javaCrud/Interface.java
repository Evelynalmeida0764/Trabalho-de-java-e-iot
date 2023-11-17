package com.javaCrud;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;

import com.google.cloud.firestore.Firestore;

public class Interface {
    public static void startInterface(Firestore db) {
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
                
        int porcentagemCalculada = 50;
        miJLabelTexto.setText("Porcentagem: " + porcentagemCalculada + "%");

        JLabel miJLabelTexto2 = new JLabel("Molhe em:");
        miJLabelTexto2.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 40));
                
        int molheEm = 3;
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

                List<SensorData> sensorDataList = FirestoreOperations.readCollectionData(db, "plantacao");

                DefaultTableModel model = new DefaultTableModel();
                model.addColumn("Data");
                model.addColumn("Humidade Ar");
                model.addColumn("Humidade Solo");
                model.addColumn("Temperatura");

                for (SensorData sensorData : sensorDataList) {
                    model.addRow(new Object[]{sensorData.getDatetime(), sensorData.getUmidadeAr(), sensorData.getUmidadeSolo(), sensorData.getTemperatura()});
                }

                JTable tabela = new JTable(model);
                JScrollPane tabelaScrollPane = new JScrollPane(tabela);
                tabelaScrollPane.setPreferredSize(new Dimension(500, 200));

                JPanel painelMaisInfo = new JPanel();

                painelMaisInfo.setLayout(new GridBagLayout());
                GridBagConstraints gbcMaisInfo = new GridBagConstraints();

                JLabel labelPorcentagem = new JLabel("Porcentagem:");
                labelPorcentagem.setFont(new java.awt.Font("arial", java.awt.Font.PLAIN, 30));
                labelPorcentagem.setText("Porcentagem: " + porcentagemCalculada + "%");
                gbcMaisInfo.gridx = 5;
                gbcMaisInfo.gridy = 0;
                painelMaisInfo.add(labelPorcentagem, gbcMaisInfo);

                JLabel labelUltimaRega = new JLabel("Ultima Rega:");
                labelUltimaRega.setFont(new java.awt.Font("arial", java.awt.Font.PLAIN, 30));
                labelUltimaRega.setText("Ultima Rega: " + "15/11/2023");
                gbcMaisInfo.gridx = 5;
                gbcMaisInfo.gridy = 3;
                painelMaisInfo.add(labelUltimaRega, gbcMaisInfo);

                gbcMaisInfo.gridx = 0;
                gbcMaisInfo.gridy = 1;
                painelMaisInfo.add(outraImagemLabel, gbcMaisInfo);

                gbcMaisInfo.gridx = 2;
                gbcMaisInfo.gridy = 2;
                gbcMaisInfo.gridheight = 2;
                gbcMaisInfo.insets = new Insets(0, 70, 0, 20);
                painelMaisInfo.add(tabelaScrollPane, gbcMaisInfo);

                novaJanela.add(painelMaisInfo);
                novaJanela.setVisible(true);
            }
        });

        miJFrame.add(miJPanel);

        miJFrame.setVisible(true);
        miJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

// Classe para criar uma borda arredondada
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
