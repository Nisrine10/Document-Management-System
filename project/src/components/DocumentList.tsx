import React from 'react';
import { Link } from 'react-router-dom';
import { File, Trash2, Share2, ExternalLink } from 'lucide-react';
import { formatDistanceToNow, isValid, parseISO } from 'date-fns';

interface Document {
  id: string;
  titre: string;
  contenu:string;
  createdAt: string;
  updatedAt: string;
}

interface DocumentListProps {
  documents: Document[];
  onDelete: (id: string) => void;
  onShare: (id: string) => void;
}

const formatDate = (dateString: string) => {
  if (!dateString) return '';
  const date = parseISO(dateString);
  return isValid(date) ? formatDistanceToNow(date, { addSuffix: true }) : '';
};

export default function DocumentList({ documents, onDelete, onShare }: DocumentListProps) {
  return (
    <div className="bg-white shadow-sm rounded-lg overflow-hidden">
      <ul className="divide-y divide-gray-200">
        {documents.map((doc) => (
          <li key={doc.id} className="p-4 hover:bg-gray-50">
            <div className="flex items-center justify-between">
              <Link 
                to={`/documents/${doc.id}`}
                className="flex items-center flex-1 min-w-0"
              >
                <File className="h-5 w-5 text-gray-400 flex-shrink-0" />
                <div className="ml-3 flex-1 min-w-0">
                  <div className="flex items-center gap-2">
                    <p className="text-sm font-medium text-gray-900 truncate">{doc.titre}</p>
                    <ExternalLink className="h-3 w-3 text-gray-400" />
                  </div>
                  <div className="flex gap-4">
                    {doc.createdAt && (
                      <p className="text-sm text-gray-500">
                        Created {formatDate(doc.createdAt)}
                      </p>
                    )}
                    {doc.updatedAt && (
                      <p className="text-sm text-gray-500">
                        Modified {formatDate(doc.updatedAt)}
                      </p>
                    )}
                  </div>
                </div>
              </Link>
              <div className="flex items-center gap-2 ml-4">
                <button
                  onClick={() => onShare(doc.id)}
                  className="p-1 text-gray-400 hover:text-indigo-600"
                >
                  <Share2 className="h-5 w-5" />
                </button>
                <button
                  onClick={() => onDelete(doc.id)}
                  className="p-1 text-gray-400 hover:text-red-600"
                >
                  <Trash2 className="h-5 w-5" />
                </button>
              </div>
            </div>
          </li>
        ))}
      </ul>
    </div>
  );
}