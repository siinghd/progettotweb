import PySimpleGUI as sg
import random

patternArray = []
rowArray = []

for i in range(10):
    for j in range(10):
        n = 0
        rowArray.append(str(n))
    patternArray.append(rowArray)
    rowArray = []


sg.theme('SandyBeach')
heading = [' 0 ', ' 1 ', ' 2 ',' 3 ',' 4 ',' 5 ', ' 6 ', ' 7 ',' 8 ', ' 9 ']
headings = [' 0 ', ' 1 ', ' 2 ',' 3 ']

col1 = [[sg.Table(values=patternArray,
                    headings=headings, 
                    max_col_width=35,
                    text_color= 'black',
                    auto_size_columns=True,
                    background_color='Antiquewhite1',
                    justification='center',
                    alternating_row_color='Antiquewhite2',
                    num_rows=4,
                    key='-TABLE1-',
                    row_height=25,
                    visible=False)],
        [sg.Text(text='',font=("Times New Roman",12),pad=10,visible=False,key='finded1')]
    ]
col2 = [[sg.Table(values=patternArray,
                    headings=headings, 
                    max_col_width=35,
                    text_color= 'black',
                    auto_size_columns=True,
                    background_color='Antiquewhite1',
                    justification='center',
                    alternating_row_color='Antiquewhite2',
                    num_rows=4,
                    key='-TABLE2-',
                    row_height=25,
                    visible=False)],
        [sg.Text(text='',font=("Times New Roman",12),pad=10,visible=False,key='finded2')]
    ]
col3 = [[sg.Table(values=patternArray,
                    headings=headings, 
                    max_col_width=35,
                    text_color= 'black',
                    auto_size_columns=True,
                    background_color='Antiquewhite1',
                    justification='center',
                    alternating_row_color='Antiquewhite2',
                    num_rows=4,
                    key='-TABLE3-',
                    row_height=25,
                    visible=False)],
        [sg.Text(text='',font=("Times New Roman",12),pad=10,visible=False,key='finded3')]
    ]
col4 = [[sg.Table(values=patternArray,
                    headings=headings, 
                    max_col_width=35,
                    text_color= 'black',
                    auto_size_columns=True,
                    background_color='Antiquewhite1',
                    justification='center',
                    alternating_row_color='Antiquewhite2',
                    num_rows=4,
                    key='-TABLE4-',
                    row_height=25,
                    visible=False)],
        [sg.Text(text='',font=("Times New Roman",12),pad=10,visible=False,key='finded4')]
    ]


layout = [
        [sg.Text(text='Insert your Pattern',font=("Times New Roman",12))],
        [sg.Button(button_text="0",pad=(0,0),size=(4,2),button_color='DarkGoldenrod3',key=1), sg.Button(button_text="0",pad=(0,0),size=(4,2),button_color='DarkGoldenrod3',key=2), sg.Button(button_text=0,pad=(0,0),size=(4,2),button_color='DarkGoldenrod3',key=3), sg.Button(button_text="0",pad=(0,0),size=(4,2),button_color='DarkGoldenrod3',key=4)],
        [sg.Button(button_text="0",pad=(0,0),size=(4,2),button_color='DarkGoldenrod3',key=5), sg.Button(button_text="0",pad=(0,0),size=(4,2),button_color='DarkGoldenrod3',key=6), sg.Button(button_text=0,pad=(0,0),size=(4,2),button_color='DarkGoldenrod3',key=7), sg.Button(button_text="0",pad=(0,0),size=(4,2),button_color='DarkGoldenrod3',key=8)],
        [sg.Button(button_text="0",pad=(0,0),size=(4,2),button_color='DarkGoldenrod3',key=9), sg.Button(button_text="0",pad=(0,0),size=(4,2),button_color='DarkGoldenrod3',key=10), sg.Button(button_text=0,pad=(0,0),size=(4,2),button_color='DarkGoldenrod3',key=11), sg.Button(button_text="0",pad=(0,0),size=(4,2),button_color='DarkGoldenrod3',key=12)],
        [sg.Button(button_text="0",pad=(0,0),size=(4,2),button_color='DarkGoldenrod3',key=13), sg.Button(button_text="0",pad=(0,0),size=(4,2),button_color='DarkGoldenrod3',key=14), sg.Button(button_text=0,pad=(0,0),size=(4,2),button_color='DarkGoldenrod3',key=15), sg.Button(button_text="0",pad=(0,0),size=(4,2),button_color='DarkGoldenrod3',key=16)],
        [sg.Button(button_text="Search",font=("Times New Roman",12),size=(6,1),button_color='DarkGoldenrod3',key='SEARCH')],
        [sg.Table(values=patternArray,
                    headings=heading, 
                    max_col_width=35,
                    text_color= 'black',
                    auto_size_columns=True,
                    background_color='Antiquewhite1',
                    justification='center',
                    alternating_row_color='Antiquewhite2',
                    num_rows=10,
                    key='-TABLE-',
                    row_height=25)],
        [sg.HorizontalSeparator(color='black',pad=20)],
        [sg.Text(text='Pattern finded',font=("Times New Roman",12),pad=10,visible=False,key='finded')],
        [sg.Column(col1,background_color='snow2',element_justification='c'),sg.Column(col2,background_color='snow2',element_justification='c'),sg.Column(col3,background_color='snow2',element_justification='c'),sg.Column(col4,background_color='snow2',element_justification='c')],
           
    ]
def returnColumnCord(arr):
    values= '\n'
    i = 0
    for x in arr:
        i+=1
        values+=f'{x[0]}|'
        if(i==4):
            i=0
            values+='\n'
    return values


def searchPattern():
    pattern = [ [window.Element(1).get_text(),window.Element(2).get_text(),window.Element(3).get_text(),window.Element(4).get_text(),],
                [window.Element(5).get_text(),window.Element(6).get_text(),window.Element(7).get_text(),window.Element(8).get_text(),],
                [window.Element(9).get_text(),window.Element(10).get_text(),window.Element(11).get_text(),window.Element(12).get_text(),],
                [window.Element(13).get_text(),window.Element(14).get_text(),window.Element(15).get_text(),window.Element(16).get_text(),],
            ]
    matrix = window.Element('-TABLE-').get()
    r = 0
    c = 0
    cord0=[]
    cord90 = []
    cord180 = []
    cord270 = []
    endMatrix=False
    pattern90 = RotateMatrix(pattern)   
    pattern180 = RotateMatrix(pattern90)
    pattern270 = RotateMatrix(pattern180)
   
    while endMatrix == False:

        if c+3 == len(matrix[0]):
            c=0
            r+=1

        if r+3 == len(matrix):
            endMatrix=True

        else:
            cord0.append(patternIterator(pattern, matrix, r, c))
            cord90.append(patternIterator(pattern90, matrix, r, c))
            cord180.append(patternIterator(pattern180, matrix, r, c))
            cord270.append(patternIterator(pattern270, matrix, r, c))
            c+=1

    cord0 = list(filter(None, cord90))
    cord90 = list(filter(None, cord90))
    cord180 = list(filter(None, cord180))
    cord270 = list(filter(None, cord270))
    window.Element('finded').update(visible=True)
    window.Element('-TABLE1-').update(values = pattern, visible=True)
    window.Element('-TABLE2-').update(values = pattern90, visible=True)
    window.Element('-TABLE3-').update(values = pattern180, visible=True)
    window.Element('-TABLE4-').update(values = pattern270, visible=True)

    window.Element('finded1').update('pattern with normal degree findend:' + str(len(cord0)) + ' at cord' + returnColumnCord(cord0) ,visible=True)
    window.Element('finded2').update('pattern with 90 degree findend:' + str(len(cord90)) + ' at cord' + returnColumnCord(cord90) ,visible=True)
    window.Element('finded3').update('pattern with 180 degree findend:' + str(len(cord180)) + ' at cord' + returnColumnCord(cord180) ,visible=True)
    window.Element('finded4').update('pattern with 270 degree findend:' + str(len(cord270)) + ' at cord' + returnColumnCord(cord270) ,visible=True)
    
    
            
def patternIterator(pattern, matrix, r, c):
    trovato=True
    i=0
    j=0
    cord = []
    while i<4 and trovato==True:
        
        while j<4 and trovato==True:
            
            if (pattern[i][j] != matrix[r+i][c+j]):
                trovato =  False
            j+=1

        i+=1
        j=0

    if trovato == True:
        cord.append([ r, c, r+3, c+3 ])
        return cord


def RotateMatrix(pattern): 
    aux=[[0,0,0,0],
         [0,0,0,0],
         [0,0,0,0],
         [0,0,0,0],]
    j=3
    i=0
    n=0
    m=0
    while j>=0:
        while i<=3:
            aux[n][m] = pattern[i][j]
            i+=1
            m+=1

        n+=1
        j=j-1
        i=0
        m=0

    return aux


window = sg.Window("Pattern", layout , background_color='snow2',element_justification='c')


while True:
    event, values =window.read()
    if event == sg.WIN_CLOSED:
        break
    if event == 'SEARCH':
        searchPattern()
        
    else:
        if 1 <= event <= 16:
            if window[event].get_text() == "1":
                window.Element(event).update(text="0",)
            else:
                window.Element(event).update(text="1",) 
    



window.close()