/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import mdlaf.MaterialLookAndFeel;

/**
 *
 * @author Daniel
 */
public class mainInterface extends javax.swing.JFrame {

    private MouseAdapter mouseListener;
    private ActionListener actionListener;
    
    /**
     * Creates new form mainInterface
     */
    public mainInterface() {
        initComponents();
//        this.btnProducts.setContentAreaFilled(true);
//        this.btnBuscarProducts.setContentAreaFilled(true);

        createListeners();
    }
    
    private void createListeners()
    {
        mouseListener = new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {                
                onEnterButton( (JButton)evt.getSource());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                onExitButton( (JButton)evt.getSource());
            }
        };
        
        actionListener = new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onActionButton( (JButton)evt.getSource() );
            }
        };
        
        this.btnStock.addMouseListener( mouseListener );
        this.btnReceiving.addMouseListener( mouseListener );
        this.btnShipment.addMouseListener( mouseListener );
        this.btnSeparation.addMouseListener( mouseListener );
        this.btnProfile.addMouseListener( mouseListener );
        this.btnProducts.addMouseListener( mouseListener );
        
        this.btnStock.addActionListener( actionListener );
        this.btnReceiving.addActionListener( actionListener );
        this.btnShipment.addActionListener( actionListener );
        this.btnSeparation.addActionListener( actionListener );
        this.btnProfile.addActionListener( actionListener );
        this.btnProducts.addActionListener( actionListener );
        
    }
    
    private void onEnterButton( JButton button )
    {
        if ( !button.isSelected() )
        {    
            button.setContentAreaFilled( true );
            button.setBackground(new Color(102, 153, 255));
        }
    }
    
    private void onExitButton( JButton button )
    {
        if ( !button.isSelected() )
        {    
            button.setContentAreaFilled( false );
        }
    }
    
    private void onActionButton( JButton button )
    {
        clearSelection();
        
        button.setSelected( true );
        button.setContentAreaFilled( true );
        
        changePanel( button );
    }
    
    private void clearSelection()
    {
        this.btnProducts.setContentAreaFilled(false);
        this.btnStock.setContentAreaFilled(false);
        this.btnReceiving.setContentAreaFilled(false);
        this.btnShipment.setContentAreaFilled(false);
        this.btnProfile.setContentAreaFilled(false);        
        this.btnSeparation.setContentAreaFilled(false);
        
        this.btnProducts.setSelected(false);
        this.btnStock.setSelected(false);
        this.btnReceiving.setSelected(false);
        this.btnShipment.setSelected(false);
        this.btnProfile.setSelected(false);        
        this.btnSeparation.setSelected(false);
    }
    
    private JPanel getButtonPanel( JButton button )
    {
        JPanel result = null;
        
        switch( button.getText())
        {
            case "Produtos":
                result = productsPanel;
                break;
                
            case "Estoque":
                result = stockPanel;
                break;
                
            case "Recebimento":
                result = receivePanel;
                break;
                
            case "Separação":
                result = separationPanel;
                break;
                
            case "Carregamento":
                result = shipmentPanel;
                break;
                
//            case "Perfil":
//                result = ;
//                break;
                
        }
        
        return result;
    }
    
  
    
    private void changePanel( JButton button )
    {
        JPanel panel = getButtonPanel( button );
      
        
        button.setBackground(new Color(102, 153, 255));
        
        //remove panels
        this.panelChange.removeAll();
        this.panelChange.repaint();
        this.panelChange.revalidate();
        
        //adding panels
        this.panelChange.add( panel );
        this.panelChange.repaint();
        this.panelChange.revalidate();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        picturePanel = new javax.swing.JPanel();
        iconImage = new javax.swing.JLabel();
        sideMenu = new javax.swing.JPanel();
        btnReceiving = new javax.swing.JButton();
        btnProducts = new javax.swing.JButton();
        btnShipment = new javax.swing.JButton();
        btnStock = new javax.swing.JButton();
        btnSeparation = new javax.swing.JButton();
        btnProfile = new javax.swing.JButton();
        btnLogout = new javax.swing.JButton();
        panelChange = new javax.swing.JPanel();
        stockPanel = new javax.swing.JPanel();
        upMenuStock = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        mainPanelStock = new javax.swing.JPanel();
        receivePanel = new javax.swing.JPanel();
        upMenuReceive = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        mainPanelReceive = new javax.swing.JPanel();
        separationPanel = new javax.swing.JPanel();
        upMenuSeparation = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        mainPanelSeparation = new javax.swing.JPanel();
        shipmentPanel = new javax.swing.JPanel();
        upMenuShipment = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        mainPanelShipment = new javax.swing.JPanel();
        productsPanel = new javax.swing.JPanel();
        upMenuProducts = new javax.swing.JPanel();
        btnHomeProducts = new javax.swing.JButton();
        btnBuscarProducts = new javax.swing.JButton();
        btnCadastrar = new javax.swing.JButton();
        mainProductPanel = new javax.swing.JPanel();
        productsSearchPanel = new javax.swing.JPanel();
        separatorUpMenu = new javax.swing.JSeparator();
        scrollPanelTableProducts = new javax.swing.JScrollPane();
        tableProducts = new javax.swing.JTable();
        lbListaProdutos = new javax.swing.JLabel();
        lbQuantidade = new javax.swing.JLabel();
        lbTotalQuantidade = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        tfSearchProduct = new javax.swing.JTextField();
        btnSearchProducts = new javax.swing.JButton();
        lbValidade = new javax.swing.JLabel();
        choiceValidade1 = new java.awt.Choice();
        lbTipo = new javax.swing.JLabel();
        choiceTipo1 = new java.awt.Choice();
        lbValidade1 = new javax.swing.JLabel();
        choiceTipo = new java.awt.Choice();
        lbValidade2 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        productRegisterPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jTextField1 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnSearch = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel6 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jComboBox5 = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        jComboBox6 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        productHomePanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        mainPanel.setPreferredSize(new java.awt.Dimension(1280, 720));

        picturePanel.setBackground(new java.awt.Color(0, 0, 153));

        iconImage.setBackground(new java.awt.Color(102, 153, 255));
        iconImage.setForeground(new java.awt.Color(204, 204, 255));
        iconImage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        iconImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon_WMS.png"))); // NOI18N
        iconImage.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout picturePanelLayout = new javax.swing.GroupLayout(picturePanel);
        picturePanel.setLayout(picturePanelLayout);
        picturePanelLayout.setHorizontalGroup(
            picturePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(picturePanelLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(iconImage)
                .addContainerGap(39, Short.MAX_VALUE))
        );
        picturePanelLayout.setVerticalGroup(
            picturePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(picturePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(iconImage)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        sideMenu.setBackground(new java.awt.Color(0, 0, 153));

        btnReceiving.setBackground(new java.awt.Color(153, 153, 153));
        btnReceiving.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnReceiving.setForeground(new java.awt.Color(255, 255, 255));
        btnReceiving.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Receive.png"))); // NOI18N
        btnReceiving.setMnemonic('C');
        btnReceiving.setText("Recebimento");
        btnReceiving.setBorder(null);
        btnReceiving.setBorderPainted(false);
        btnReceiving.setContentAreaFilled(false);
        btnReceiving.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnReceiving.setIconTextGap(40);
        btnReceiving.setInheritsPopupMenu(true);

        btnProducts.setBackground(new java.awt.Color(102, 153, 255));
        btnProducts.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnProducts.setForeground(new java.awt.Color(255, 255, 255));
        btnProducts.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Shopping_Car.png"))); // NOI18N
        btnProducts.setMnemonic('A');
        btnProducts.setText("Produtos");
        btnProducts.setBorder(null);
        btnProducts.setBorderPainted(false);
        btnProducts.setContentAreaFilled(false);
        btnProducts.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnProducts.setIconTextGap(68);

        btnShipment.setBackground(new java.awt.Color(153, 153, 153));
        btnShipment.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnShipment.setForeground(new java.awt.Color(255, 255, 255));
        btnShipment.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Shipment.png"))); // NOI18N
        btnShipment.setMnemonic('E');
        btnShipment.setText("Carregamento");
        btnShipment.setBorder(null);
        btnShipment.setBorderPainted(false);
        btnShipment.setContentAreaFilled(false);
        btnShipment.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnShipment.setIconTextGap(35);
        btnShipment.setInheritsPopupMenu(true);

        btnStock.setBackground(new java.awt.Color(153, 153, 153));
        btnStock.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnStock.setForeground(new java.awt.Color(255, 255, 255));
        btnStock.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Stock.png"))); // NOI18N
        btnStock.setMnemonic('B');
        btnStock.setText("Estoque");
        btnStock.setBorder(null);
        btnStock.setBorderPainted(false);
        btnStock.setContentAreaFilled(false);
        btnStock.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnStock.setIconTextGap(75);

        btnSeparation.setBackground(new java.awt.Color(153, 153, 153));
        btnSeparation.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnSeparation.setForeground(new java.awt.Color(255, 255, 255));
        btnSeparation.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Shopping_Basket.png"))); // NOI18N
        btnSeparation.setMnemonic('D');
        btnSeparation.setText("Separação");
        btnSeparation.setBorder(null);
        btnSeparation.setBorderPainted(false);
        btnSeparation.setContentAreaFilled(false);
        btnSeparation.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnSeparation.setIconTextGap(60);
        btnSeparation.setInheritsPopupMenu(true);

        btnProfile.setBackground(new java.awt.Color(153, 153, 153));
        btnProfile.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnProfile.setForeground(new java.awt.Color(255, 255, 255));
        btnProfile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/User.png"))); // NOI18N
        btnProfile.setMnemonic('F');
        btnProfile.setText("Perfil");
        btnProfile.setBorder(null);
        btnProfile.setBorderPainted(false);
        btnProfile.setContentAreaFilled(false);
        btnProfile.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnProfile.setIconTextGap(95);
        btnProfile.setInheritsPopupMenu(true);

        btnLogout.setBackground(new java.awt.Color(153, 153, 153));
        btnLogout.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnLogout.setForeground(new java.awt.Color(255, 255, 255));
        btnLogout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Logout.png"))); // NOI18N
        btnLogout.setText("Logout");
        btnLogout.setBorder(null);
        btnLogout.setBorderPainted(false);
        btnLogout.setContentAreaFilled(false);
        btnLogout.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnLogout.setIconTextGap(85);
        btnLogout.setInheritsPopupMenu(true);
        btnLogout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLogoutMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLogoutMouseExited(evt);
            }
        });

        javax.swing.GroupLayout sideMenuLayout = new javax.swing.GroupLayout(sideMenu);
        sideMenu.setLayout(sideMenuLayout);
        sideMenuLayout.setHorizontalGroup(
            sideMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnReceiving, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnProducts, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnStock, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnSeparation, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
            .addComponent(btnProfile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnLogout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnShipment, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        sideMenuLayout.setVerticalGroup(
            sideMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sideMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnProducts, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnStock, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(btnReceiving, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSeparation, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnShipment, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(btnProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelChange.setLayout(new java.awt.CardLayout());

        upMenuStock.setBackground(new java.awt.Color(51, 51, 51));

        jButton2.setText("STOCK");

        javax.swing.GroupLayout upMenuStockLayout = new javax.swing.GroupLayout(upMenuStock);
        upMenuStock.setLayout(upMenuStockLayout);
        upMenuStockLayout.setHorizontalGroup(
            upMenuStockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, upMenuStockLayout.createSequentialGroup()
                .addContainerGap(837, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addGap(383, 383, 383))
        );
        upMenuStockLayout.setVerticalGroup(
            upMenuStockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, upMenuStockLayout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addContainerGap())
        );

        mainPanelStock.setBackground(new java.awt.Color(0, 51, 102));
        mainPanelStock.setLayout(new java.awt.CardLayout());

        javax.swing.GroupLayout stockPanelLayout = new javax.swing.GroupLayout(stockPanel);
        stockPanel.setLayout(stockPanelLayout);
        stockPanelLayout.setHorizontalGroup(
            stockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(upMenuStock, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(mainPanelStock, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        stockPanelLayout.setVerticalGroup(
            stockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(stockPanelLayout.createSequentialGroup()
                .addComponent(upMenuStock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(mainPanelStock, javax.swing.GroupLayout.DEFAULT_SIZE, 687, Short.MAX_VALUE))
        );

        panelChange.add(stockPanel, "card3");

        upMenuReceive.setBackground(new java.awt.Color(51, 51, 51));

        jButton1.setText("Recebimento");

        javax.swing.GroupLayout upMenuReceiveLayout = new javax.swing.GroupLayout(upMenuReceive);
        upMenuReceive.setLayout(upMenuReceiveLayout);
        upMenuReceiveLayout.setHorizontalGroup(
            upMenuReceiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, upMenuReceiveLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(477, 477, 477))
        );
        upMenuReceiveLayout.setVerticalGroup(
            upMenuReceiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, upMenuReceiveLayout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
        );

        mainPanelReceive.setBackground(new java.awt.Color(0, 51, 102));
        mainPanelReceive.setLayout(new java.awt.CardLayout());

        javax.swing.GroupLayout receivePanelLayout = new javax.swing.GroupLayout(receivePanel);
        receivePanel.setLayout(receivePanelLayout);
        receivePanelLayout.setHorizontalGroup(
            receivePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(upMenuReceive, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(mainPanelReceive, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1285, Short.MAX_VALUE)
        );
        receivePanelLayout.setVerticalGroup(
            receivePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(receivePanelLayout.createSequentialGroup()
                .addComponent(upMenuReceive, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(mainPanelReceive, javax.swing.GroupLayout.DEFAULT_SIZE, 687, Short.MAX_VALUE))
        );

        panelChange.add(receivePanel, "card3");

        upMenuSeparation.setBackground(new java.awt.Color(51, 51, 51));

        jButton4.setText("separation");

        javax.swing.GroupLayout upMenuSeparationLayout = new javax.swing.GroupLayout(upMenuSeparation);
        upMenuSeparation.setLayout(upMenuSeparationLayout);
        upMenuSeparationLayout.setHorizontalGroup(
            upMenuSeparationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, upMenuSeparationLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton4)
                .addGap(477, 477, 477))
        );
        upMenuSeparationLayout.setVerticalGroup(
            upMenuSeparationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, upMenuSeparationLayout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addComponent(jButton4)
                .addContainerGap())
        );

        mainPanelSeparation.setBackground(new java.awt.Color(0, 51, 102));
        mainPanelSeparation.setLayout(new java.awt.CardLayout());

        javax.swing.GroupLayout separationPanelLayout = new javax.swing.GroupLayout(separationPanel);
        separationPanel.setLayout(separationPanelLayout);
        separationPanelLayout.setHorizontalGroup(
            separationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(upMenuSeparation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(mainPanelSeparation, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1285, Short.MAX_VALUE)
        );
        separationPanelLayout.setVerticalGroup(
            separationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(separationPanelLayout.createSequentialGroup()
                .addComponent(upMenuSeparation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(mainPanelSeparation, javax.swing.GroupLayout.DEFAULT_SIZE, 687, Short.MAX_VALUE))
        );

        panelChange.add(separationPanel, "card3");

        upMenuShipment.setBackground(new java.awt.Color(51, 51, 51));

        jButton5.setText("SHIPMENT");

        javax.swing.GroupLayout upMenuShipmentLayout = new javax.swing.GroupLayout(upMenuShipment);
        upMenuShipment.setLayout(upMenuShipmentLayout);
        upMenuShipmentLayout.setHorizontalGroup(
            upMenuShipmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, upMenuShipmentLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton5)
                .addGap(477, 477, 477))
        );
        upMenuShipmentLayout.setVerticalGroup(
            upMenuShipmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, upMenuShipmentLayout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addComponent(jButton5)
                .addContainerGap())
        );

        mainPanelShipment.setBackground(new java.awt.Color(0, 51, 102));
        mainPanelShipment.setLayout(new java.awt.CardLayout());

        javax.swing.GroupLayout shipmentPanelLayout = new javax.swing.GroupLayout(shipmentPanel);
        shipmentPanel.setLayout(shipmentPanelLayout);
        shipmentPanelLayout.setHorizontalGroup(
            shipmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(upMenuShipment, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(mainPanelShipment, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1285, Short.MAX_VALUE)
        );
        shipmentPanelLayout.setVerticalGroup(
            shipmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(shipmentPanelLayout.createSequentialGroup()
                .addComponent(upMenuShipment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(mainPanelShipment, javax.swing.GroupLayout.DEFAULT_SIZE, 687, Short.MAX_VALUE))
        );

        panelChange.add(shipmentPanel, "card3");

        productsPanel.setBackground(new java.awt.Color(255, 255, 255));
        productsPanel.setAutoscrolls(true);
        productsPanel.setRequestFocusEnabled(false);

        upMenuProducts.setBackground(new java.awt.Color(76, 76, 150));

        btnHomeProducts.setBackground(new java.awt.Color(102, 153, 255));
        btnHomeProducts.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnHomeProducts.setForeground(new java.awt.Color(255, 255, 255));
        btnHomeProducts.setText("Home");
        btnHomeProducts.setBorder(null);
        btnHomeProducts.setBorderPainted(false);
        btnHomeProducts.setContentAreaFilled(false);
        btnHomeProducts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHomeProductsActionPerformed(evt);
            }
        });

        btnBuscarProducts.setBackground(new java.awt.Color(102, 153, 255));
        btnBuscarProducts.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnBuscarProducts.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscarProducts.setText("Buscar");
        btnBuscarProducts.setBorder(null);
        btnBuscarProducts.setBorderPainted(false);
        btnBuscarProducts.setContentAreaFilled(false);
        btnBuscarProducts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarProductsActionPerformed(evt);
            }
        });

        btnCadastrar.setBackground(new java.awt.Color(255, 255, 255));
        btnCadastrar.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnCadastrar.setForeground(new java.awt.Color(255, 255, 255));
        btnCadastrar.setText("Opções");
        btnCadastrar.setBorder(null);
        btnCadastrar.setBorderPainted(false);
        btnCadastrar.setContentAreaFilled(false);
        btnCadastrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCadastrarMouseClicked(evt);
            }
        });
        btnCadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadastrarActionPerformed(evt);
            }
        });

        mainProductPanel.setLayout(new java.awt.CardLayout());

        productsSearchPanel.setBackground(new java.awt.Color(255, 255, 255));
        productsSearchPanel.setAutoscrolls(true);

        separatorUpMenu.setBackground(new java.awt.Color(153, 153, 153));

        scrollPanelTableProducts.setBorder(null);

        tableProducts.setBackground(new java.awt.Color(204, 204, 204));
        tableProducts.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        tableProducts.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"", null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nome", "Peso", "Origem", "Tipo", "Validade", "Refrigerado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableProducts.setCellSelectionEnabled(true);
        tableProducts.setGridColor(new java.awt.Color(255, 255, 255));
        tableProducts.setIntercellSpacing(new java.awt.Dimension(20, 20));
        tableProducts.setSelectionBackground(new java.awt.Color(51, 51, 51));
        scrollPanelTableProducts.setViewportView(tableProducts);

        lbListaProdutos.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbListaProdutos.setForeground(new java.awt.Color(255, 255, 255));
        lbListaProdutos.setText("Lista de Produtos");

        lbQuantidade.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lbQuantidade.setForeground(new java.awt.Color(255, 255, 255));
        lbQuantidade.setText("Quantidade:");

        lbTotalQuantidade.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lbTotalQuantidade.setForeground(new java.awt.Color(255, 255, 255));
        lbTotalQuantidade.setText("0");

        jPanel2.setBackground(new java.awt.Color(95, 95, 150));

        tfSearchProduct.setText("Nome");
        tfSearchProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfSearchProductActionPerformed(evt);
            }
        });

        btnSearchProducts.setBackground(new java.awt.Color(153, 153, 153));
        btnSearchProducts.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnSearchProducts.setForeground(new java.awt.Color(255, 255, 255));
        btnSearchProducts.setText("Pesquisar");
        btnSearchProducts.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 0, true));
        btnSearchProducts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchProductsActionPerformed(evt);
            }
        });

        lbValidade.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbValidade.setForeground(new java.awt.Color(255, 255, 255));
        lbValidade.setText("Validade:");

        choiceValidade1.setBackground(new java.awt.Color(204, 204, 204));

        lbTipo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbTipo.setForeground(new java.awt.Color(255, 255, 255));
        lbTipo.setText("Tipo:");

        choiceTipo1.setBackground(new java.awt.Color(204, 204, 204));

        lbValidade1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbValidade1.setForeground(new java.awt.Color(255, 255, 255));
        lbValidade1.setText("Origem:");

        choiceTipo.setBackground(new java.awt.Color(204, 204, 204));

        lbValidade2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbValidade2.setForeground(new java.awt.Color(255, 255, 255));
        lbValidade2.setText("Peso:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(tfSearchProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 483, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSearchProducts, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbValidade)
                            .addComponent(lbValidade1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(choiceValidade1, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                            .addComponent(choiceTipo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(16, 16, 16)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbTipo)
                            .addComponent(lbValidade2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(choiceTipo1, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                            .addComponent(jTextField5))))
                .addContainerGap(67, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfSearchProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearchProducts, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(choiceTipo1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(choiceValidade1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addComponent(lbValidade)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(18, 18, 18))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(lbTipo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lbValidade2)
                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lbValidade1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(choiceTipo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(34, 34, 34))
        );

        javax.swing.GroupLayout productsSearchPanelLayout = new javax.swing.GroupLayout(productsSearchPanel);
        productsSearchPanel.setLayout(productsSearchPanelLayout);
        productsSearchPanelLayout.setHorizontalGroup(
            productsSearchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(separatorUpMenu)
            .addGroup(productsSearchPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(productsSearchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(productsSearchPanelLayout.createSequentialGroup()
                        .addComponent(scrollPanelTableProducts, javax.swing.GroupLayout.DEFAULT_SIZE, 849, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbListaProdutos))
                    .addGroup(productsSearchPanelLayout.createSequentialGroup()
                        .addComponent(lbQuantidade)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbTotalQuantidade))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(284, Short.MAX_VALUE))
        );
        productsSearchPanelLayout.setVerticalGroup(
            productsSearchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(productsSearchPanelLayout.createSequentialGroup()
                .addComponent(separatorUpMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(productsSearchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(productsSearchPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lbListaProdutos))
                    .addGroup(productsSearchPanelLayout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(scrollPanelTableProducts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(17, 17, 17)))
                .addGap(78, 78, 78)
                .addGroup(productsSearchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbQuantidade)
                    .addComponent(lbTotalQuantidade))
                .addGap(44, 44, 44))
        );

        mainProductPanel.add(productsSearchPanel, "card2");

        productRegisterPanel.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(95, 95, 150));
        jPanel1.setPreferredSize(new java.awt.Dimension(1280, 720));
        jPanel1.setLayout(null);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Nome:");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(10, 50, 40, 20);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel1.add(jComboBox1);
        jComboBox1.setBounds(57, 128, 411, 30);
        jPanel1.add(jTextField1);
        jTextField1.setBounds(57, 48, 411, 30);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Validade:");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(550, 50, 64, 20);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Endereço da prateleira:");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(13, 166, 139, 20);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Refrigerado:");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(550, 130, 73, 20);

        btnSearch.setBackground(new java.awt.Color(51, 51, 51));
        btnSearch.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnSearch.setForeground(new java.awt.Color(255, 255, 255));
        btnSearch.setText("Cadastrar");
        btnSearch.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 0, true));
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });
        jPanel1.add(btnSearch);
        btnSearch.setBounds(1072, 290, 146, 29);

        jCheckBox1.setOpaque(false);
        jPanel1.add(jCheckBox1);
        jCheckBox1.setBounds(630, 130, 21, 21);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Peso:");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(10, 90, 40, 20);

        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });
        jPanel1.add(jTextField2);
        jTextField2.setBounds(620, 50, 437, 30);
        jPanel1.add(jTextField3);
        jTextField3.setBounds(57, 88, 410, 30);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Origem:");
        jPanel1.add(jLabel7);
        jLabel7.setBounds(550, 90, 49, 20);
        jPanel1.add(jTextField4);
        jTextField4.setBounds(620, 90, 437, 30);

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Tipo:");
        jPanel1.add(jLabel8);
        jLabel8.setBounds(10, 130, 40, 20);

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel1.add(jComboBox2);
        jComboBox2.setBounds(34, 206, 176, 30);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("ID:");
        jPanel1.add(jLabel9);
        jLabel9.setBounds(13, 204, 17, 20);

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Rua:");
        jPanel1.add(jLabel10);
        jLabel10.setBounds(228, 204, 26, 20);

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel1.add(jComboBox3);
        jComboBox3.setBounds(511, 206, 196, 30);

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Coluna:");
        jPanel1.add(jLabel11);
        jLabel11.setBounds(717, 204, 46, 20);

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel1.add(jComboBox4);
        jComboBox4.setBounds(258, 206, 203, 30);

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Bloco:");
        jPanel1.add(jLabel12);
        jLabel12.setBounds(471, 204, 36, 20);

        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel1.add(jComboBox5);
        jComboBox5.setBounds(773, 206, 196, 30);

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Nível:");
        jPanel1.add(jLabel13);
        jLabel13.setBounds(979, 204, 33, 20);

        jComboBox6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel1.add(jComboBox6);
        jComboBox6.setBounds(1022, 206, 196, 30);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("CADASTRO DE PRODUTOS");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(13, 3, 217, 25);

        javax.swing.GroupLayout productRegisterPanelLayout = new javax.swing.GroupLayout(productRegisterPanel);
        productRegisterPanel.setLayout(productRegisterPanelLayout);
        productRegisterPanelLayout.setHorizontalGroup(
            productRegisterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(productRegisterPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1265, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        productRegisterPanelLayout.setVerticalGroup(
            productRegisterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(productRegisterPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(343, Short.MAX_VALUE))
        );

        mainProductPanel.add(productRegisterPanel, "card3");

        productHomePanel.setBackground(new java.awt.Color(0, 102, 204));

        javax.swing.GroupLayout productHomePanelLayout = new javax.swing.GroupLayout(productHomePanel);
        productHomePanel.setLayout(productHomePanelLayout);
        productHomePanelLayout.setHorizontalGroup(
            productHomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1285, Short.MAX_VALUE)
        );
        productHomePanelLayout.setVerticalGroup(
            productHomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 695, Short.MAX_VALUE)
        );

        mainProductPanel.add(productHomePanel, "card4");

        javax.swing.GroupLayout upMenuProductsLayout = new javax.swing.GroupLayout(upMenuProducts);
        upMenuProducts.setLayout(upMenuProductsLayout);
        upMenuProductsLayout.setHorizontalGroup(
            upMenuProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(upMenuProductsLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(upMenuProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(mainProductPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(upMenuProductsLayout.createSequentialGroup()
                        .addComponent(btnHomeProducts, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBuscarProducts, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        upMenuProductsLayout.setVerticalGroup(
            upMenuProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(upMenuProductsLayout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(upMenuProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnHomeProducts, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscarProducts, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addComponent(mainProductPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 695, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout productsPanelLayout = new javax.swing.GroupLayout(productsPanel);
        productsPanel.setLayout(productsPanelLayout);
        productsPanelLayout.setHorizontalGroup(
            productsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(upMenuProducts, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        productsPanelLayout.setVerticalGroup(
            productsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(productsPanelLayout.createSequentialGroup()
                .addComponent(upMenuProducts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        panelChange.add(productsPanel, "card2");
        productsPanel.getAccessibleContext().setAccessibleName("");

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(sideMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(picturePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0)
                .addComponent(panelChange, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addComponent(picturePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(sideMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(panelChange, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 1506, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 950, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLogoutMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLogoutMouseEntered
        this.btnLogout.setContentAreaFilled(true);
        this.btnLogout.setBackground(new Color(249, 74, 95));
    }//GEN-LAST:event_btnLogoutMouseEntered

    private void btnLogoutMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLogoutMouseExited
        this.btnLogout.setContentAreaFilled(false);
    }//GEN-LAST:event_btnLogoutMouseExited

    private void btnSearchProductsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchProductsActionPerformed

    }//GEN-LAST:event_btnSearchProductsActionPerformed

    private void tfSearchProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfSearchProductActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfSearchProductActionPerformed

    private void btnBuscarProductsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarProductsActionPerformed
        this.btnCadastrar.setContentAreaFilled(false);
        this.btnHomeProducts.setContentAreaFilled(false);
        this.btnBuscarProducts.setContentAreaFilled(true);
        this.btnBuscarProducts.setBackground(new Color(102, 153, 255));
        
        //remove panels
        this.mainProductPanel.removeAll();
        this.mainProductPanel.repaint();
        this.mainProductPanel.revalidate();

        //adding panels
        this.mainProductPanel.add(this.productsSearchPanel);
        this.mainProductPanel.repaint();
        this.mainProductPanel.revalidate();
    }//GEN-LAST:event_btnBuscarProductsActionPerformed

    private void btnCadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadastrarActionPerformed
       
        this.btnBuscarProducts.setContentAreaFilled(false);
        this.btnHomeProducts.setContentAreaFilled(false);
        
        this.btnCadastrar.setContentAreaFilled(true);
        this.btnCadastrar.setBackground(new Color(102, 153, 255));

        //remove panels
        this.mainProductPanel.removeAll();
        this.mainProductPanel.repaint();
        this.mainProductPanel.revalidate();

        //adding panels
        this.mainProductPanel.add(this.productRegisterPanel);
        this.mainProductPanel.repaint();
        this.mainProductPanel.revalidate();
    }//GEN-LAST:event_btnCadastrarActionPerformed

    private void btnCadastrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCadastrarMouseClicked
       //cadastrar os produtos
        
    }//GEN-LAST:event_btnCadastrarMouseClicked

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
       //procurar produtos
    }//GEN-LAST:event_btnSearchActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void btnHomeProductsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHomeProductsActionPerformed
        this.btnBuscarProducts.setContentAreaFilled(false);
        this.btnCadastrar.setContentAreaFilled(false);

        this.btnHomeProducts.setContentAreaFilled(true);
        this.btnHomeProducts.setBackground(new Color(102, 153, 255));

        //remove panels
        this.mainProductPanel.removeAll();
        this.mainProductPanel.repaint();
        this.mainProductPanel.revalidate();

        //adding panels
        this.mainProductPanel.add(this.productHomePanel);
        this.mainProductPanel.repaint();
        this.mainProductPanel.revalidate();    
    

       
    }//GEN-LAST:event_btnHomeProductsActionPerformed


    /**
     * @param args the command line arguments
     */
    /*public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        /*try {
            UIManager.setLookAndFeel (new MaterialLookAndFeel ());*/
        
        //</editor-fold>

        /* Create and display the form 
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new mainInterface().setVisible(true);
            }
        });
    }   catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(mainInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscarProducts;
    private javax.swing.JButton btnCadastrar;
    private javax.swing.JButton btnHomeProducts;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnProducts;
    private javax.swing.JButton btnProfile;
    private javax.swing.JButton btnReceiving;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnSearchProducts;
    private javax.swing.JButton btnSeparation;
    private javax.swing.JButton btnShipment;
    private javax.swing.JButton btnStock;
    private java.awt.Choice choiceTipo;
    private java.awt.Choice choiceTipo1;
    private java.awt.Choice choiceValidade1;
    private javax.swing.JLabel iconImage;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JComboBox<String> jComboBox6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JLabel lbListaProdutos;
    private javax.swing.JLabel lbQuantidade;
    private javax.swing.JLabel lbTipo;
    private javax.swing.JLabel lbTotalQuantidade;
    private javax.swing.JLabel lbValidade;
    private javax.swing.JLabel lbValidade1;
    private javax.swing.JLabel lbValidade2;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel mainPanelReceive;
    private javax.swing.JPanel mainPanelSeparation;
    private javax.swing.JPanel mainPanelShipment;
    private javax.swing.JPanel mainPanelStock;
    private javax.swing.JPanel mainProductPanel;
    private javax.swing.JPanel panelChange;
    private javax.swing.JPanel picturePanel;
    private javax.swing.JPanel productHomePanel;
    private javax.swing.JPanel productRegisterPanel;
    private javax.swing.JPanel productsPanel;
    private javax.swing.JPanel productsSearchPanel;
    private javax.swing.JPanel receivePanel;
    private javax.swing.JScrollPane scrollPanelTableProducts;
    private javax.swing.JPanel separationPanel;
    private javax.swing.JSeparator separatorUpMenu;
    private javax.swing.JPanel shipmentPanel;
    private javax.swing.JPanel sideMenu;
    private javax.swing.JPanel stockPanel;
    private javax.swing.JTable tableProducts;
    private javax.swing.JTextField tfSearchProduct;
    private javax.swing.JPanel upMenuProducts;
    private javax.swing.JPanel upMenuReceive;
    private javax.swing.JPanel upMenuSeparation;
    private javax.swing.JPanel upMenuShipment;
    private javax.swing.JPanel upMenuStock;
    // End of variables declaration//GEN-END:variables
}
